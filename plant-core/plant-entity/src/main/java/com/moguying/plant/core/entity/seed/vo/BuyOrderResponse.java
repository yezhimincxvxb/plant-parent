package com.moguying.plant.core.entity.seed.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BuyOrderResponse implements Serializable {


    private static final long serialVersionUID = 4898881393676586830L;

    @JSONField(ordinal = 1)
    private String seedName;

    @JSONField(ordinal = 2)
    private Integer growDays;

    @JSONField(ordinal = 3, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal perPrice;

    @JSONField(ordinal = 4)
    private Integer buyCount;

    @JSONField(ordinal = 5, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal buyAmount;

    @JSONField(ordinal = 6)
    private Integer orderId;

    @JSONField(ordinal = 7, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal availableMoney;

    @JSONField(ordinal = 8)
    private Integer leftSecond;

    @JSONField(ordinal = 9)
    private String orderNumber;

    @JSONField(ordinal = 10, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal perWeigh;

    @JSONField(ordinal = 11)
    private Integer seedTypeId;
}
