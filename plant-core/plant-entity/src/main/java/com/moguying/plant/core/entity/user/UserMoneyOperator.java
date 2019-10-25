package com.moguying.plant.core.entity.user;

import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.core.entity.account.UserMoney;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 账户操作类
 */
@Data
public class UserMoneyOperator {

    private UserMoney userMoney;

    private MoneyOpEnum opType;

    private String operationId;

    private BigDecimal affectMoney;

    private String mark;


    @Override
    public String toString() {
        return "UserMoneyOperator{" +
                "userMoney=" + userMoney +
                ", opType=" + opType +
                ", operationId=" + operationId +
                ", affectMoney=" + affectMoney +
                ", mark='" + mark + '\'' +
                '}';
    }
}
