package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderSearch {
    @JSONField(ordinal = 1)
    private Integer page;

    @JSONField(ordinal = 2)
    private Integer size;

    @JSONField(ordinal = 3)
    private Integer state;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "OrderSearch{" +
                "page=" + page +
                ", size=" + size +
                ", state=" + state +
                '}';
    }
}
