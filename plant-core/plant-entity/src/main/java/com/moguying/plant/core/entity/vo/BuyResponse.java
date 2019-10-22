package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.dto.UserAddress;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BuyResponse implements Serializable {

    @JSONField(ordinal = 1)
    private UserAddress address;

    /**
     * 如果是现金支付，则totalAmount表示：运费 + 订单总价
     * 如果是蘑菇币兑换，则totalAmount仅表示：运费
     */
    @JSONField(ordinal = 2,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    @JSONField(ordinal = 3)
    private String orderNumber;

    @JSONField(ordinal = 4)
    private Integer orderId;

    @JSONField(ordinal = 5)
    private Integer leftSecond;

    @JSONField(ordinal = 6)
    private Integer totalCoins;

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getLeftSecond() {
        return leftSecond;
    }

    public void setLeftSecond(Integer leftSecond) {
        this.leftSecond = leftSecond;
    }
}
