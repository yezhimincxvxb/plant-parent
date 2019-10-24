package com.moguying.plant.core.entity.seed.vo;

import lombok.Data;

import java.util.List;

@Data
public class PlantOrder {

    /**
     * 种植的菌包订单id
     */
    private Integer orderId;


    /**
     * 种植份数
     */
    private Integer plantCount;

    /**
     * 种植棚区id
     */
    private Integer blockId;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 券id
     */
    private List<Integer> fertilizerIds;
}
