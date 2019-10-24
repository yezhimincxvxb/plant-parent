package com.moguying.plant.core.entity.dto;

import com.moguying.plant.constant.MoneyOpEnum;
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


    public BigDecimal getAffectMoney() {
        return affectMoney;
    }

    public void setAffectMoney(BigDecimal affectMoney) {
        this.affectMoney = affectMoney;
    }

    public UserMoney getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(UserMoney userMoney) {
        this.userMoney = userMoney;
    }

    public MoneyOpEnum getOpType() {
        return opType;
    }

    public void setOpType(MoneyOpEnum opType) {
        this.opType = opType;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

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
