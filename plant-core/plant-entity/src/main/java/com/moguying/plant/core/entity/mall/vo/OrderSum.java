package com.moguying.plant.core.entity.mall.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSum {
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 1)
    private BigDecimal totalAmount;

    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 2)
    private BigDecimal productAmount;

    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 3)
    private BigDecimal expressFee;

    @JSONField(ordinal = 4)
    private Integer buyCount;

    @JSONField(ordinal = 5)
    private Integer totalCoins;

    /**
     * 使用券金额
     */
    @JSONField(ordinal = 6, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal fertilizerAmount;

}
