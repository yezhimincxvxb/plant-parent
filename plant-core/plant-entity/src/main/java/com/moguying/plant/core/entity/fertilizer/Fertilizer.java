package com.moguying.plant.core.entity.fertilizer;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_fertilizer
 * @author 
 */
@Data
public class Fertilizer implements Serializable {
    /**
     * id
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 券类型
     */
    @JSONField(ordinal = 2)
    private Integer typeId;

    /**
     * 券最小金额
     */
    @JSONField(ordinal = 3,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMin;

    /**
     * 券最大金额
     */
    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal amountMax;

    /**
     * 券数量
     */
    @JSONField(ordinal = 5)
    private Integer count;

    /**
     * 券可使用的用户等级
     *
     */
    @JSONField(ordinal = 6)
    private Integer userLevel;

    /**
     * 有效期
     */
    @JSONField(ordinal = 10,format = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;

    /**
     * 是否无使用限制[1是，0否]
     */
    @JSONField(ordinal = 11)
    private Integer useNoLimit;

    /**
     * 在指定大棚
     */
    @JSONField(ordinal = 12)
    private Integer useInBlock;

    /**
     * 在指定商品
     */
    @JSONField(ordinal = 13)
    private Integer useInProduct;

    /**
     * 添加时间
     */
    @JSONField(ordinal = 14,format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 券使用事件
     */
    @JSONField(ordinal = 15)
    private String triggerUseEvent;

    /**
     * 券获取事件
     */
    @JSONField(ordinal = 16)
    private String triggerGetEvent;

    /**
     * 每次触发发多少张券给用户
     */
    @JSONField(ordinal = 17)
    private Integer perCount;

    /**
     * 券开始时间
     */
    @JSONField(ordinal = 18,format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 券在哪种菌包类型使用
     */
    @JSONField(ordinal = 19)
    private Integer useInSeedType;


    @JSONField(ordinal = 20)
    private String typeName;

    @JSONField(ordinal = 21)
    private String useInSeedTypeName;

    @JSONField(ordinal = 22)
    private String useInBlockNumber;

    @JSONField(ordinal = 23)
    private String useInProductName;

    @JSONField(ordinal = 24)
    private Integer expireDays;

    @JSONField(ordinal = 25)
    private Boolean isSingleTrigger;

    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 26)
    private BigDecimal fertilizerAmount;

    private Boolean fertilizerAmountIsRate;

    /**
     * 蘑菇币兑换成券
     */
    private Integer coinFertilizer;

}