package com.moguying.plant.core.entity.reap.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.math.BigDecimal;

public class SaleResponse {
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal saleAmount;

    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }
}
