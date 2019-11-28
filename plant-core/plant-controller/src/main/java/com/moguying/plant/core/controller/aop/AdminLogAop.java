package com.moguying.plant.core.controller.aop;

import com.alibaba.fastjson.JSONObject;
import com.moguying.plant.constant.ApiEnum;
import com.moguying.plant.core.entity.admin.AdminLog;
import com.moguying.plant.core.entity.admin.AdminUser;
import com.moguying.plant.core.entity.system.vo.SessionAdminUser;
import com.moguying.plant.core.service.admin.AdminLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    @Before(value = "addAdminLog()")
    public void doBefore() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute(SessionAdminUser.sessionKey);
        AdminLog adminLog = new AdminLog();
        adminLog.setActionCode(request.getRequestURL().toString());
        adminLog.setActionParam(JSONObject.toJSONString(request.getParameterMap()));
        adminLog.setAddTime(new Date());
        adminLog.setUserId(adminUser.getId());
        adminLogService.save(adminLog);
    }

}
