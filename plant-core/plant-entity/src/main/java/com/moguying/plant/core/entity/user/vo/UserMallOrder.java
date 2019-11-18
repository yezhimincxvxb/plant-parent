package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserMallOrder {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @JSONField(ordinal = 3)
    private Integer state;

    @JSONField(ordinal = 4)
    private String stateStr;

    @JSONField(ordinal = 5, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    @JSONField(ordinal = 6, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal buyAmount;

    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal feeAmount;

    @JSONField(ordinal = 11)
    private Integer totalCoins;

    @JSONField(ordinal = 8)
    private Integer productCount;

    @JSONField(ordinal = 9)
    private List<OrderItem> orderItems;

    @JSONField(ordinal = 10, name = "isNotice")
    private Boolean isNotice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Boolean getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Boolean notice) {
        isNotice = notice;
    }


}
