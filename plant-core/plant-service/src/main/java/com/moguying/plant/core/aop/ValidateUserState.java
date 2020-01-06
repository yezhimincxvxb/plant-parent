package com.moguying.plant.core.aop;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.UserEnum;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.user.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateUserState {

    @Autowired
    UserService userService;

    @Pointcut("@annotation(com.moguying.plant.core.annotation.ValidateUser) && args(userId,..)")
    private void validatePoint(Integer userId) {
    }

    @Around("validatePoint(userId)")
    private Object validate(ProceedingJoinPoint proceedingJoinPoint, Integer userId) throws Throwable {
        // 用户不存在
        if (null == userId)
            return new ResponseData<>(MessageEnum.USER_NOT_EXISTS.getMessage(), MessageEnum.USER_NOT_EXISTS.getState());
        // 提示登录
        User user = userService.userInfoById(userId);
        if (user == null) {
            return new ResponseData<>(MessageEnum.NEED_LOGIN.getMessage(), MessageEnum.NEED_LOGIN.getState());
        }
        // 实名认证
        if (!user.getIsRealName().equals(UserEnum.USER_PAYMENT_ACCOUNT_VERIFY_SUCCESS.getState())) {
            return new ResponseData<>(MessageEnum.USER_NEED_REAL_NAME.getMessage(), MessageEnum.USER_NEED_REAL_NAME.getState());
        }
        // 支付密码
        if (null == user.getPaymentAccount() || !user.getPaymentState().equals(UserEnum.USER_PAYMENT_ACCOUNT_REGISTER.getState())) {
            return new ResponseData<>(MessageEnum.USER_NEED_REGISTER_PAYMENT_ACCOUNT.getMessage(),
                    MessageEnum.USER_NEED_REGISTER_PAYMENT_ACCOUNT.getState());
        }
        // 绑定银行卡
        if (!user.getIsBindCard()) {
            return new ResponseData<>(MessageEnum.USER_NOT_BIND_CARD.getMessage(),
                    MessageEnum.USER_NOT_BIND_CARD.getState());
        }
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }


}

