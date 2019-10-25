package com.moguying.plant.core.entity.reap.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleResponse {
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal saleAmount;
}
