package com.moguying.plant.core.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_seed_order_detail
 * @author 
 */
public class SeedOrderDetail implements Serializable, PayOrder {

    @JSONField(ordinal = 1)
    @Excel(name = "序号")
    private Integer id;

    /**
     * 订单编号
     */
    @JSONField(ordinal = 2)
    @Excel(name = "流水号")
    private String orderNumber;

    /**
     * 种子id
     */
    @JSONField(ordinal = 3)
    private Integer seedId;

    @JSONField(ordinal = 4)
    private String seedName;

    @JSONField(ordinal = 5)
    @Excel(name = "菌包类型名称")
    private String seedTypeName;

    @JSONField(ordinal = 6)
    @Excel(name = "生长周期")
    private Integer seedGrowDays;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    private BigDecimal seedPrice;

    /**
     * 用户id
     */
    @JSONField(ordinal = 8)
    private Integer userId;

    /**
     * 购买份数
     */
    @JSONField(ordinal = 9)
    @Excel(name = "购买份数")
    private Integer buyCount;

    /**
     * 购买总价
     */
    @JSONField(ordinal = 10,serializeUsing = BigDecimalSerialize.class)
    @Excel(name = "购买总价")
    private BigDecimal buyAmount;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 11)
    @Excel(name = "添加时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 支付时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 12)
    @Excel(name = "支付时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 支付短信流水号
     */
    @JSONField(ordinal = 13)
    private String seqNo;

    /**
     * 余额支付金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 14)
    @Excel(name = "余额支付")
    private BigDecimal accountPayAmount;

    /**
     * 卡支付金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 15)
    @Excel(name = "卡支付")
    private BigDecimal carPayAmount;


    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 16)
    @Excel(name = "关单时间",format = "yyyy-MM-dd HH:mm:ss")
    private Date closeTime;

    @JSONField(ordinal = 17)
    @Excel(name = "状态",replace = {"待支付_0","已支付_1","已取消_2"})
    private Integer state;

    /**
     * 辅助字段，菌包图片
     */
    @JSONField(ordinal = 18)
    private String picUrl;

    /**
     * 辅助字段,用户手机号
     */
    @JSONField(ordinal = 19)
    @Excel(name = "手机号")
    private String phone;

    /**
     * 优惠金额
     */
    @JSONField(ordinal = 20,serializeUsing = BigDecimalSerialize.class)
    @Excel(name = "优惠金额")
    private BigDecimal reducePayAmount;

    @JSONField(ordinal = 21,serialize = false)
    private Integer seedTypeId;

    /**
     * 实际支付金额
     */
    @JSONField(ordinal = 22,deserialize = false,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal realPayAmount;

    @JSONField(ordinal = 23)
    @Excel(name = "姓名")
    private String realName;

    /**
     * 查询辅助时间
     */
    private Date startTime;

    /**
     * 查询辅助时间
     */
    private Date endTime;

    private static final long serialVersionUID = 1L;

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

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

    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public String getSeedTypeName() {
        return seedTypeName;
    }

    public void setSeedTypeName(String seedTypeName) {
        this.seedTypeName = seedTypeName;
    }

    public Integer getSeedGrowDays() {
        return seedGrowDays;
    }

    public void setSeedGrowDays(Integer seedGrowDays) {
        this.seedGrowDays = seedGrowDays;
    }

    public BigDecimal getSeedPrice() {
        return seedPrice;
    }

    public void setSeedPrice(BigDecimal seedPrice) {
        this.seedPrice = seedPrice;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public BigDecimal getAccountPayAmount() {
        return accountPayAmount;
    }

    public void setAccountPayAmount(BigDecimal accountPayAmount) {
        this.accountPayAmount = accountPayAmount;
    }

    public BigDecimal getCarPayAmount() {
        return carPayAmount;
    }

    public void setCarPayAmount(BigDecimal carPayAmount) {
        this.carPayAmount = carPayAmount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public MoneyOpEnum getOpType() {
        return MoneyOpEnum.BUY_SEED_ORDER;
    }

    public BigDecimal getReducePayAmount() {
        return reducePayAmount;
    }

    public void setReducePayAmount(BigDecimal reducePayAmount) {
        this.reducePayAmount = reducePayAmount;
    }

    public Integer getSeedTypeId() {
        return seedTypeId;
    }

    public void setSeedTypeId(Integer seedTypeId) {
        this.seedTypeId = seedTypeId;
    }

    public BigDecimal getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(BigDecimal realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    @Override
    public BigDecimal getFeeAmount() {
        return BigDecimal.ZERO;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}