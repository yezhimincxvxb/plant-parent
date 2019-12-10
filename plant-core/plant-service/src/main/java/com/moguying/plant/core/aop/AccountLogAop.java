package com.moguying.plant.core.aop;

import com.moguying.plant.core.entity.account.UserMoneyLog;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.service.account.UserMoneyLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Aspect
@Slf4j
public class AccountLogAop {

    @Autowired
    UserMoneyLogService userMoneyLogService;

    @Pointcut("execution(* com.moguying.plant.core.service.account.UserMoneyService.updateAccount(..))")
    public void accountLog() {
    }


    @AfterReturning(returning = "result", pointcut = "accountLog()")
    public void addAccountLog(JoinPoint joinPoint, Object result) {
        UserMoneyOperator operator = null;

        if (result != null) {
            operator = (UserMoneyOperator) result;
            UserMoneyLog userMoneyLog = new UserMoneyLog();
            userMoneyLog.setUserId(operator.getUserMoney().getUserId());
            //如果影响金额为空，则不记录
            if (null == operator.getAffectMoney())
                return;
            userMoneyLog.setAffectMoney(operator.getAffectMoney());
            userMoneyLog.setAvailableMoney(operator.getUserMoney().getAvailableMoney());
            userMoneyLog.setFreezeMoney(operator.getUserMoney().getFreezeMoney());
            userMoneyLog.setCollectMoney(operator.getUserMoney().getCollectCapital().add(operator.getUserMoney().getCollectInterest()));
            userMoneyLog.setCollectCapital(operator.getUserMoney().getCollectCapital());
            userMoneyLog.setCollectInterest(operator.getUserMoney().getCollectInterest());
            userMoneyLog.setAffectType(operator.getOpType().getType().byteValue());
            userMoneyLog.setDetailId(operator.getOperationId() != null ? operator.getOperationId() : "0");
            userMoneyLog.setAffectTime(new Timestamp(System.currentTimeMillis()));
            userMoneyLog.setAffectInfo(operator.getMark() == null ? operator.getOpType().getTypeStr() : operator.getMark());
            userMoneyLogService.addLog(userMoneyLog);
        }
    }

}
