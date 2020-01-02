package com.moguying.plant.core.entity.index;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CapitalChange {

    /**
     * 预计利息
     */
    @JSONField(ordinal = 1, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal preProfit;

    /**
     * 预计本金
     */
    @JSONField(ordinal = 2, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal preAmount;

    /**
     * 预计采摘金额
     */
    @JSONField(ordinal = 3, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        if (this.preProfit != null && this.preAmount != null)
            return this.preProfit.add(this.preAmount);
        return totalAmount;
    }
}
