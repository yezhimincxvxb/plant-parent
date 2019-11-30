package com.moguying.plant.core.entity.fertilizer;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("plant_fertilizer")
@Data
public class Fertilizer implements Serializable {

    private static final long serialVersionUID = 5450928262436171453L;

    /**
     * id
     */
    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 券类型
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer typeId;

    /**
     * 券最小金额
     */
    @JSONField(ordinal = 3, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal amountMin;

    /**
     * 券最大金额
     */
    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal amountMax;

    /**
     * 券数量
     */
    @JSONField(ordinal = 5)
    @TableField
    private Integer count;

    /**
     * 券可使用的用户等级
     */
    @JSONField(ordinal = 6)
    @TableField
    private Integer userLevel;

    /**
     * 有效期
     */
    @JSONField(ordinal = 10, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date expireTime;

    /**
     * 是否无使用限制[1是，0否]
     */
    @JSONField(ordinal = 11)
    @TableField
    private Integer useNoLimit;

    /**
     * 在指定大棚
     */
    @JSONField(ordinal = 12)
    @TableField
    private Integer useInBlock;

    /**
     * 在指定商品
     */
    @JSONField(ordinal = 13)
    @TableField
    private Integer useInProduct;

    /**
     * 添加时间
     */
    @JSONField(ordinal = 14, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date addTime;

    /**
     * 券使用事件
     */
    @JSONField(ordinal = 15)
    @TableField
    private String triggerUseEvent;

    /**
     * 券获取事件
     */
    @JSONField(ordinal = 16)
    @TableField
    private String triggerGetEvent;

    /**
     * 每次触发发多少张券给用户
     */
    @JSONField(ordinal = 17)
    @TableField
    private Integer perCount;

    /**
     * 券开始时间
     */
    @JSONField(ordinal = 18, format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date startTime;

    /**
     * 券在哪种菌包类型使用
     */
    @JSONField(ordinal = 19)
    @TableField
    private Integer useInSeedType;

    @Excel(name = "券名称")
    @JSONField(ordinal = 20)
    @TableField(exist = false)
    private String typeName;

    @JSONField(ordinal = 21)
    @TableField(exist = false)
    private String useInSeedTypeName;

    @JSONField(ordinal = 22)
    @TableField(exist = false)
    private String useInBlockNumber;

    @JSONField(ordinal = 23)
    @TableField(exist = false)
    private String useInProductName;

    @JSONField(ordinal = 24)
    @TableField
    private Integer expireDays;

    @JSONField(ordinal = 25)
    @TableField
    private Boolean isSingleTrigger;

    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 26)
    @TableField
    private BigDecimal fertilizerAmount;

    @TableField
    private Boolean fertilizerAmountIsRate;

    /**
     * 蘑菇币兑换成券
     */
    @TableField
    private Integer coinFertilizer;

}