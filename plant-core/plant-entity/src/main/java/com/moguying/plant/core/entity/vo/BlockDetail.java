package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BlockDetail implements Serializable {

    @JSONField(ordinal = 1)
    private Integer blockId;

    //棚区编号
    @JSONField(ordinal = 2)
    private String blockNumber;

    //种植菌包类型名
    @JSONField(ordinal = 3)
    private String seedTypeName;

    //棚区总额
    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal blockAmount;

    //菌包生长周期
    @JSONField(ordinal = 5)
    private Integer seedGrowDays;

    //菌包单价
    @JSONField(ordinal = 6,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal seedPrice;

    //棚区剩余份数
    @JSONField(ordinal = 7)
    private Integer blockLeftCount;

    //一份出售价格
    @JSONField(ordinal = 8,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal saleAmount;

    //最低种植份数
    @JSONField(ordinal = 9)
    private Integer mintPlant;

    @JSONField(ordinal = 10)
    private Integer plantPercent;

    @JSONField(ordinal = 11)
    private String picUrl;

}
