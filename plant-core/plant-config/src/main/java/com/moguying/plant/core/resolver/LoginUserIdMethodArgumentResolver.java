package com.moguying.plant.core.resolver;

import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.constant.ApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Slf4j
@Component
public class LoginUserIdMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if(methodParameter.getParameterType().isAssignableFrom(Integer.class)
            && methodParameter.hasParameterAnnotation(LoginUserId.class)
        ) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Integer loginUserId = (Integer) nativeWebRequest.getAttribute(ApiEnum.LOGIN_USER_ID.getTypeStr(), RequestAttributes.SCOPE_REQUEST);
        if(null != loginUserId)
            return loginUserId;
        return 0;
    }
}
