package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class HomeSeed {

    @JSONField(ordinal = 1)
    private Integer id;

    @JSONField(ordinal = 2)
    private String name;

    @JSONField(ordinal = 3)
    private Integer leftCount;

    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal perPrice;

    @JSONField(ordinal = 5)
    private String picUrl;

    @JSONField(ordinal = 6,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal interestRates;

    @JSONField(ordinal = 7)
    private Integer growDays;

    @JSONField(ordinal = 8,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal totalInterest;

    //每百份获利
    @JSONField(ordinal = 9,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal perHundredProfit;

    @JSONField(ordinal = 10)
    private Integer state;
}
