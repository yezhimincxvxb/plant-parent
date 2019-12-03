package com.moguying.plant.core.controller.aop;

import com.alibaba.fastjson.JSONObject;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.admin.AdminLog;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class AdminLogAop {

    @Autowired
    AdminLogService adminLogService;

    @Pointcut(value = "execution(public * com.moguying.plant.core.controller.back.*.*(..))")
    public void addAdminLog() {
    }

    /**
     * 记录用户登录后的操作记录
     */
    @After(value = "addAdminLog()")
    public void doBefore(JoinPoint joinPoint) {
        //过滤NoLogin操作
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        NoLogin annotations = methodSignature.getMethod().getAnnotation(NoLogin.class);
        if(null != annotations) return;
        Optional<ServletRequestAttributes> attributes = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if(!attributes.isPresent()) return;
        HttpServletRequest request = attributes.get().getRequest();
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute(SessionAdminUser.sessionKey);
        AdminLog adminLog = new AdminLog();
        adminLog.setActionCode(request.getRequestURL().toString());
        adminLog.setActionParam(JSONObject.toJSONString(request.getParameterMap()));
        adminLog.setAddTime(new Date());
        adminLog.setUserId(adminUser.getId());
        if (null!=adminLog.getUserId())
            adminLogService.save(adminLog);
    }

}
