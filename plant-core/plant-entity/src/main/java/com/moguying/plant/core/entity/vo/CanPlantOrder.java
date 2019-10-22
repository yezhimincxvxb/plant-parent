package com.moguying.plant.core.entity.vo;

/**
 * 可在指定棚区种植的订单信息
 */
public class CanPlantOrder {

    private Integer count;

    private Integer orderId;

    public CanPlantOrder() {
    }

    public CanPlantOrder(Integer count, Integer orderId) {
        this.count = count;
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
