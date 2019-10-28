package com.moguying.plant.core.entity.bargain.vo;

import lombok.Data;

@Data
public class ShareResult {

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 信息说明
     */
    private String message;

    public ShareResult setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public ShareResult setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public ShareResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
