package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Calculation {

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal price;

    private Integer days;

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal rate;

    private Integer count;
}
