package com.moguying.plant.core.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_mall_order_detail
 * @author 
 */
@Data
public class MallOrderDetail implements Serializable {
    private Integer id;

    /**
     * 订单流水号
     */
    private Integer orderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 购买总价
     */
    private BigDecimal buyAmount;

    /**
     * 订单蘑菇币
     */
    private Integer buyCoins;

    private static final long serialVersionUID = 1L;

}