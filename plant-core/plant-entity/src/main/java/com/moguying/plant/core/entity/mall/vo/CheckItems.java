package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class CheckItems {
    @JSONField(ordinal = 1)
    private List<OrderItem> items;

    @JSONField(ordinal = 2)
    private Boolean check;
}
