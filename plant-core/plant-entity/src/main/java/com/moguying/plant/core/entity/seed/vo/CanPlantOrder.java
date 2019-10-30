package com.moguying.plant.core.entity.seed.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 可在指定棚区种植的订单信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanPlantOrder {

    private Integer count;

    private Integer orderId;
}
