package com.moguying.plant.core.aop;

import com.moguying.plant.core.entity.dto.SaleCoinLog;
import com.moguying.plant.core.entity.dto.UserSaleCoin;
import com.moguying.plant.core.service.reap.SaleCoinLogService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserMoneyAop {

    @Autowired
    private SaleCoinLogService saleCoinLogService;

    @Pointcut("execution(* com.moguying.plant.core.service.reap.SaleCoinService.updateSaleCoin(..))")
    public void userMoneyLog(){}

    @AfterReturning( returning = "result",pointcut = "userMoneyLog()")
    public void addUserMoneyLog(Object result){
        if (result != null){
            UserSaleCoin userSaleCoin = (UserSaleCoin) result;
            if (userSaleCoin.getAffectCoin() == 0) return;
            SaleCoinLog saleCoinLog = new SaleCoinLog();
            saleCoinLog.setUserId(userSaleCoin.getUserId());
            saleCoinLog.setAffectCoin(userSaleCoin.getAffectCoin());
            saleCoinLog.setAffectType(userSaleCoin.getAffectType());
            saleCoinLog.setAffectDetailId(userSaleCoin.getAffectDetailId());
            saleCoinLogService.insertSaleCoinLog(saleCoinLog);
        }
    }
}
