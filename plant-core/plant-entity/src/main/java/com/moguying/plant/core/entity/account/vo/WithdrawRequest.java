package com.moguying.plant.core.entity.account.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawRequest implements Serializable {

    private static final long serialVersionUID = -4306427271971509256L;

    private Integer bankId;

    private BigDecimal money;

    private String code;

    /**
     * 在到账时使用
     */
    private Integer withdrawId;
    /**
     * 在到账时使用
     */
    private String smsCode;
    /**
     * 在到账时使用
     */
    private String seqNo;

    /**
     * 在到账时使用
     */
    private String orderNumber;


    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(Integer withdrawId) {
        this.withdrawId = withdrawId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
