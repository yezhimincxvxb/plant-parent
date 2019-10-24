package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BankCarSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_user_bank
 * @author 
 */
public class UserBank implements Serializable {

    @JSONField(ordinal = 1)
    private Integer id;
    /**
     * 用户id
     */
    @JSONField(ordinal = 2,serialize = false)
    private Integer userId;

    /**
     * 银行卡号
     */
    @JSONField(serializeUsing = BankCarSerialize.class,ordinal = 3)
    private String bankNumber;

    /**
     * 流水号
     */
    @JSONField(ordinal = 4)
    private String orderNumber;

    /**
     * 所属银行代码
     */
    @JSONField(ordinal = 5)
    private String bankId;

    /**
     * 银行卡类型
     */
    @JSONField(ordinal = 6)
    private String cardType;

    /**
     * 银行名称
     */
    @JSONField(ordinal = 7,name = "bankName")
    private String bankAddress;

    /**
     * 绑卡时间
     */
    @JSONField(serialize = false,deserialize = false,ordinal = 8)
    private Date addTime;

    /**
     * 卡片状态[0使用中，1已停止]
     */
    @JSONField(ordinal = 9)
    private Integer state;

    @JSONField(serialize = false,deserialize = false,ordinal = 10)
    private String paymentAccount;

    @JSONField(ordinal = 11)
    private String bankPhone;


    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone;
    }
}