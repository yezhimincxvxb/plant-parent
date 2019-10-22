package com.moguying.plant.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.dto.AdminUser;
import com.moguying.plant.core.entity.dto.SessionAdminUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BackInterceptor implements HandlerInterceptor {

    Logger log = LoggerFactory.getLogger(BackInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        HandlerMethod method = (HandlerMethod) handler;
        if(method.getMethod().getAnnotation(NoLogin.class) != null)
            return true;

        AdminUser adminUser = (AdminUser) request.getSession().getAttribute(SessionAdminUser.sessionKey);
        if(adminUser == null) {
            String responseStr = JSON.toJSONString(new ResponseData<String>(MessageEnum.NEED_LOGIN.getMessage(), MessageEnum.NEED_LOGIN.getState()));
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(responseStr);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
