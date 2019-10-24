package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetail implements Serializable {

    @JSONField(ordinal = 1)
    private Integer orderId;

    @JSONField(ordinal = 2)
    private Integer state;

    @JSONField(ordinal = 3)
    private String stateStr;

    @JSONField(ordinal = 4)
    private UserAddress address;

    @JSONField(ordinal = 5)
    private List<OrderItem> orderItems;

    /**
     * 订单备注
     */
    @JSONField(ordinal = 6)
    private String mark;

    /**
     * 订单编号
     */
    @JSONField(ordinal = 7)
    private String orderNumber;

    /**
     * 下单时间
     */
    @JSONField(ordinal = 8,format = "yyyy.MM.dd HH:mm:ss")
    private Date addTime;

    /**
     * 支付时间
     */
    @JSONField(ordinal = 9,format = "yyyy.MM.dd HH:mm:ss")
    private Date payTime;

    /**
     * 商品总价
     */
    @JSONField(ordinal = 10,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal productAmount;

    /**
     * 总蘑菇币
     */
    @JSONField(ordinal = 18)
    private Integer totalCoins;

    /**
     * 运费
     */
    @JSONField(ordinal = 11,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal expressFee;

    /**
     * 订单总价
     */
    @JSONField(ordinal = 12,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    /**
     * 支付剩余时间
     */
    @JSONField(ordinal = 13)
    private int leftSecond;

    /**
     * 发货时间
     */
    @JSONField(ordinal = 14,format = "yyyy.MM.dd HH:mm:ss")
    private Date sendTime;


    @JSONField(ordinal = 15,name = "isNotice")
    private Boolean isNotice;

    /**
     * 收货时间
     */
    @JSONField(ordinal = 16,format = "yyyy.MM.dd HH:mm:ss")
    private Date confirmTime;

    /**
     * 取消时间
     */
    @JSONField(ordinal = 17,format = "yyyy.MM.dd HH:mm:ss")
    private Date closeTime;



    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public int getLeftSecond() {
        return leftSecond;
    }

    public void setLeftSecond(int leftSecond) {
        this.leftSecond = leftSecond;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Boolean notice) {
        isNotice = notice;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }
}
