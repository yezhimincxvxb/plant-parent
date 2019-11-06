package com.moguying.plant.core.entity.seed.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PlantOrderResponse {

    /**
     * 大棚编号
     */
    @JSONField(ordinal = 1)
    private String blockNumber;

    /**
     * 种植份数
     */
    @JSONField(ordinal = 2)
    private Integer plantCount;

    /**
     * 预期采摘时间
     */
    @JSONField(ordinal = 3,format = "MM月dd日")
    private Date preReapTime;

    private transient Integer userId;

    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantAmount;

    /**
     * 大棚id
     */
    @JSONField(serialize = false)
    private Integer blockId;

    /**
     * 菌包id
     */
    @JSONField(serialize = false)
    private Integer seedTypeId;

    /**
     * 种植菌包类型名
     */
    @JSONField
    private String seedTypeName;


    /**
     * 种植类型生长周期
     */
    @JSONField
    private Integer growDays;

    /**
     * 种植类型产出量
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal perWeigh;


    /**
     * 种植id
     */
    @JSONField
    private Integer reapId;


}
