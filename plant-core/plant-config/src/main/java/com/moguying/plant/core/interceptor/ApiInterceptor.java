package com.moguying.plant.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.moguying.plant.constant.ApiEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod method = (HandlerMethod) handler;
        if (method.getMethod().getAnnotation(NoLogin.class) != null)
            return true;

        String token = request.getHeader(ApiEnum.AUTH_TOKEN.getTypeStr());
        if (null == token || StringUtils.isEmpty(token)) {
            String jsonStr = JSON.toJSONString(new ResponseData<>(MessageEnum.NEED_LOGIN.getMessage(),
                    MessageEnum.NEED_LOGIN.getState()));
            setErrorResponse(response, jsonStr, HttpStatus.OK);
            return false;
        }
        DecodedJWT decodedJWT;
        try {
            decodedJWT = TokenUtil.INSTANCE.verify(token);
            Date iat = decodedJWT.getIssuedAt();
            long currentTime = System.currentTimeMillis();
            long refreshTime = iat.getTime() + TokenUtil.INSTANCE.activeTime;
            long expireTime = decodedJWT.getExpiresAt().getTime();

            //大于刷新时间且在有效期内，进行刷新token
            if (currentTime > refreshTime && currentTime < expireTime) {
                Map<String, String> map = new HashMap<>();
                map.put(ApiEnum.LOGIN_USER_ID.getTypeStr(), decodedJWT.getClaim(ApiEnum.LOGIN_USER_ID.getTypeStr()).asString());
                map.put(ApiEnum.LOGIN_PHONE.getTypeStr(), decodedJWT.getClaim(ApiEnum.LOGIN_PHONE.getTypeStr()).asString());
                map.put(ApiEnum.LOGIN_TIME.getTypeStr(), decodedJWT.getClaim(ApiEnum.LOGIN_TIME.getTypeStr()).asString());
                String autoTokenRe = TokenUtil.INSTANCE.updateToken(map);
                response.setHeader(ApiEnum.REFRESH_AUTH_TOKEN.getTypeStr(), autoTokenRe);
            }

            if (currentTime > expireTime) {
                throw new JWTVerificationException("Out Of Time");
            }

            Integer userId = Integer.parseInt(decodedJWT.getClaim(ApiEnum.LOGIN_USER_ID.getTypeStr()).asString());
            request.setAttribute(ApiEnum.LOGIN_USER_ID.getTypeStr(), userId);
            return true;
        } catch (JWTVerificationException e) {
            String jsonStr = JSON.toJSONString(new ResponseData<>(MessageEnum.TOKEN_ERROR.getMessage(),
                    MessageEnum.NEED_LOGIN.getState()));
            setErrorResponse(response, jsonStr, HttpStatus.OK);
            return false;
        }
    }


    private void setErrorResponse(HttpServletResponse response, String jsonStr, HttpStatus httpStatus) throws IOException {
        response.setStatus(httpStatus.value());
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(jsonStr);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
