package com.moguying.plant.core.entity.common.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Calculation implements Serializable {

    private static final long serialVersionUID = 1279676370325223855L;

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal price;

    private Integer days;

    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal rate;

    private Integer count;
}
