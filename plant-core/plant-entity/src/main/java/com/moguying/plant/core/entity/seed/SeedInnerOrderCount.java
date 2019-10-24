package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class SeedInnerOrderCount  implements Serializable {

    @JSONField(name = "seed_id")
    private Integer seedId;

    @JSONField(name = "seed_name")
    private String seedName;

    @JSONField(name = "order_count")
    private Integer orderCount;

    @JSONField(name = "order_time",format = "yyyy-MM-dd HH:mm:ss")
    private Date  orderTime;


    public Integer getSeedId() {
        return seedId;
    }

    public void setSeedId(Integer seedId) {
        this.seedId = seedId;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
