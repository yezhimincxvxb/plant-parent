package com.moguying.plant.core.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.common.util.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_seed_type
 * @author 
 */
@Data
public class SeedType implements Serializable {

    private static final long serialVersionUID = 248815661784936260L;

    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 种子种类名称
     */
    @JSONField(ordinal = 2)
    private String className;

    /**
     * 生长周期
     */
    @JSONField(ordinal = 3)
    private Integer growDays;

    /**
     * 每份价格
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 4)
    private BigDecimal perPrice;

    /**
     * 利率
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 5)
    private BigDecimal interestRates;

    /**
     * 排序
     */
    @JSONField(ordinal = 6)
    private Integer orderNumber;

    /**
     * 是否删除[0未删除，1已删除]
     */
    @JSONField(ordinal = 7)
    private Boolean isDelete;

    /**
     * 种植方式
     */
    @JSONField(ordinal = 8)
    private Integer plantType;

    /**
     * 结算方式
     */
    @JSONField(ordinal = 9)
    private Integer reapType;


    /**
     * 背景图
     */
    @JSONField(ordinal = 10)
    private String picUrl;


    /**
     * 缩略背景图
     */
    @JSONField(ordinal = 11)
    private String thumbPicUrl;


    @JSONField(ordinal = 12)
    private SeedContent content;

    /**
     * 兑换数
     */
    @JSONField(ordinal = 13)
    private Integer exchangeNum;

}