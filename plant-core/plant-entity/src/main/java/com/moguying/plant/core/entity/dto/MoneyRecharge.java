package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_money_recharge
 * @author 
 */
public class MoneyRecharge implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    @JSONField(name = "user_id")
    private Integer userId;

    @JSONField(name = "user_name",deserialize = false)
    private String userName;

    @JSONField(name = "order_number")
    private String orderNumber;

    /**
     * 充值金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal money;

    /**
     * 充值订单状态[0充值中，1充值成功，2充值失败]
     */
    private Integer state;

    /**
     * 充值时间
     */
    @JSONField(name = "recharge_time",format = "yyyy-MM-dd HH:mm:ss")
    private Date rechargeTime;

    /**
     * 到账时间
     */
    @JSONField(name = "to_account_time",format = "yyyy-MM-dd HH:mm:ss")
    private Date toAccountTime;

    /**
     * 到账金额
     */
    @JSONField(name = "to_account_money",serializeUsing = BigDecimalSerialize.class)
    private BigDecimal toAccountMoney;

    /**
     * 充值手续费
     */
    @JSONField(name = "fee",serializeUsing = BigDecimalSerialize.class)
    private BigDecimal fee;


    /**
     * 第三方支付短信流水号
     */
    @JSONField(serialize = false,deserialize = false)
    private String paySmsSqNo;


    /**
     * 充值来源
     */
    private String source;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Date getToAccountTime() {
        return toAccountTime;
    }

    public void setToAccountTime(Date toAccountTime) {
        this.toAccountTime = toAccountTime;
    }

    public BigDecimal getToAccountMoney() {
        return toAccountMoney;
    }

    public void setToAccountMoney(BigDecimal toAccountMoney) {
        this.toAccountMoney = toAccountMoney;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPaySmsSqNo() {
        return paySmsSqNo;
    }

    public void setPaySmsSqNo(String paySmsSqNo) {
        this.paySmsSqNo = paySmsSqNo;
    }

    @Override
    public String toString() {
        return "MoneyRecharge{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", money=" + money +
                ", state=" + state +
                ", rechargeTime=" + rechargeTime +
                ", toAccountTime=" + toAccountTime +
                ", toAccountMoney=" + toAccountMoney +
                ", fee=" + fee +
                ", source='" + source + '\'' +
                '}';
    }
}