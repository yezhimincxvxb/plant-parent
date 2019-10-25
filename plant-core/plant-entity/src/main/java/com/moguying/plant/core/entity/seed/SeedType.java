package com.moguying.plant.core.entity.seed;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("plant_seed_type")
@Data
public class SeedType implements Serializable {

    private static final long serialVersionUID = 248815661784936260L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 种子种类名称
     */
    @JSONField(ordinal = 2)
    @TableField
    private String className;

    /**
     * 生长周期
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer growDays;

    /**
     * 每份价格
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 4)
    @TableField
    private BigDecimal perPrice;

    /**
     * 利率
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 5)
    @TableField
    private BigDecimal interestRates;

    /**
     * 排序
     */
    @JSONField(ordinal = 6)
    @TableField
    private Integer orderNumber;

    /**
     * 是否删除[0未删除，1已删除]
     */
    @JSONField(ordinal = 7)
    @TableField
    private Boolean isDelete;

    /**
     * 种植方式
     */
    @JSONField(ordinal = 8)
    @TableField
    private Integer plantType;

    /**
     * 结算方式
     */
    @JSONField(ordinal = 9)
    @TableField
    private Integer reapType;


    /**
     * 背景图
     */
    @JSONField(ordinal = 10)
    @TableField
    private String picUrl;


    /**
     * 缩略背景图
     */
    @JSONField(ordinal = 11)
    @TableField
    private String thumbPicUrl;


    @JSONField(ordinal = 12)
    @TableField(exist = false)
    private SeedContent content;

    /**
     * 兑换数
     */
    @JSONField(ordinal = 13)
    @TableField
    private Integer exchangeNum;

}