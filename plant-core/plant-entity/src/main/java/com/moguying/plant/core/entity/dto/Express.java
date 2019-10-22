package com.moguying.plant.core.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * plant_express
 * @author 
 */
@Data
public class Express implements Serializable {
    private Integer id;

    /**
     * 收货地址id
     */
    private Integer addressId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品类型[1菌包兑换，2采摘兑换，3商城商品]
     */
    private Integer productType;

    /**
     * 快递单号
     */
    private String expressNumber;

    private static final long serialVersionUID = 1L;
}