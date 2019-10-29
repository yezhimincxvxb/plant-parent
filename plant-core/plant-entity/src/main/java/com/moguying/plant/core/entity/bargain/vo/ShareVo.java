package com.moguying.plant.core.entity.bargain.vo;

import lombok.Data;

@Data
public class ShareVo {

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

    public ShareVo setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public ShareVo setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public ShareVo setMessage(String message) {
        this.message = message;
        return this;
    }
}
