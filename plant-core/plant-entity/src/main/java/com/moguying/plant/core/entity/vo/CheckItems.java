package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class CheckItems {
    @JSONField(ordinal = 1)
    private List<OrderItem> items;

    @JSONField(ordinal = 2)
    private Boolean check;

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
