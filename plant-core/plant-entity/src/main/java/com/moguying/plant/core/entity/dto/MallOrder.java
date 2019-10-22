package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.constant.MoneyOpEnum;
import com.moguying.plant.core.entity.vo.OrderItem;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * plant_mall_order
 * @author
 *
 */
@Data
public class MallOrder implements Serializable, PayOrder {

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 订单流水号
     */
    @JSONField(ordinal = 2)
    private String orderNumber;

    /**
     * 用户id
     */
    @JSONField(ordinal = 3)
    private Integer userId;

    /**
     * 购买总价
     */
    @JSONField(ordinal = 4 ,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal buyAmount;

    /**
     * 购买总蘑菇币
     */
    @JSONField(ordinal = 26)
    private Integer totalCoins;

    /**
     * 买家备注
     */
    @JSONField(ordinal = 5)
    private String buyMark;

    /**
     * 订单快递费
     */
    @JSONField(ordinal = 6,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal feeAmount;

    /**
     * 订单状态[0未支付，1已支付（待发货），2已发货（待收货），3已完成，4已关单,5已取消]
     */
    @JSONField(ordinal = 7)
    private Integer state;

    /**
     * 用户收货地址id
     */
    @JSONField(ordinal = 8)
    private Integer addressId;

    /**
     * 下单时间
     */
    @JSONField(ordinal = 9,format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 支付时间
     */
    @JSONField(ordinal = 10,format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 关单时间
     */
    @JSONField(ordinal = 11,format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    /**
     * 发货时间
     */
    @JSONField(ordinal = 12,format = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /**
     * 是否已提醒过发货
     */
    @JSONField(ordinal = 13)
    private Boolean isNotice;

    /**
     * 确认收货时间
     */
    @JSONField(ordinal = 14,format = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /**
     * 快递单号
     */
    @JSONField(ordinal = 15)
    private String expressOrderNumber;

    /**
     * 快递公司编码
     */
    @JSONField(ordinal = 16)
    private String expressComCode;

    /**
     * 取消订单原因
     */
    @JSONField(ordinal = 26)
    private String cancelReason;

    /**
     * 支付短信流水号
     */
    @JSONField(ordinal = 17)
    private String seqNo;

    /**
     * 卡支付金额
     */
    @JSONField(ordinal = 18,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal carPayAmount;

    /**
     * 余额支付金额
     */
    @JSONField(ordinal = 19,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal accountPayAmount;


    /**
     * 优惠金额
     */
    @JSONField(ordinal = 25,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal reducePayAmount;

    /**
     * 后台辅助字段
     * 用户手机号
     */
    @JSONField(ordinal = 20)
    private String phone;

    @JSONField(ordinal = 21,format = "yyyy-MM-dd HH:mm:ss")
    private Date noticeTime;

    /**
     * 后台辅助字段
     * 用户真实姓名
     */
    @JSONField(ordinal = 22)
    private String realName;

    /**
     * 后台辅助字段
     * 订单地址详情
     */
    @JSONField(ordinal = 23)
    private UserAddress address;

    /**
     * 后台辅助字段
     * 订单商品详情
     */
    @JSONField(ordinal = 24)
    private List<OrderItem> details;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public String getBuyMark() {
        return buyMark;
    }

    public void setBuyMark(String buyMark) {
        this.buyMark = buyMark;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
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

    public void setIsNotice(Boolean isNotice) {
        this.isNotice = isNotice;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getExpressOrderNumber() {
        return expressOrderNumber;
    }

    public void setExpressOrderNumber(String expressOrderNumber) {
        this.expressOrderNumber = expressOrderNumber;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getCarPayAmount() {
        return carPayAmount;
    }

    public void setCarPayAmount(BigDecimal carPayAmount) {
        this.carPayAmount = carPayAmount;
    }

    public BigDecimal getAccountPayAmount() {
        return accountPayAmount;
    }

    public void setAccountPayAmount(BigDecimal accountPayAmount) {
        this.accountPayAmount = accountPayAmount;
    }

    @Override
    public MoneyOpEnum getOpType() {
        return MoneyOpEnum.BUY_MALL_PRODUCT;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public List<OrderItem> getDetails() {
        return details;
    }

    public void setDetails(List<OrderItem> details) {
        this.details = details;
    }

    public BigDecimal getReducePayAmount() {
        return reducePayAmount;
    }

    public void setReducePayAmount(BigDecimal reducePayAmount) {
        this.reducePayAmount = reducePayAmount;
    }

    public Boolean getNotice() {
        return isNotice;
    }

    public void setNotice(Boolean notice) {
        isNotice = notice;
    }

    public String getExpressComCode() {
        return expressComCode;
    }

    public void setExpressComCode(String expressComCode) {
        this.expressComCode = expressComCode;
    }

}