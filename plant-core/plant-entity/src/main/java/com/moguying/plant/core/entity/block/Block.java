package com.moguying.plant.core.entity.block;

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

/**
 * plant_block
 *
 * @author
 */
@TableName("plant_block")
@Data
public class Block implements Serializable {

    private static final long serialVersionUID = -8925069503420289794L;
    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 土地编号
     */
    @JSONField(ordinal = 2)
    @TableField
    private String number;

    /**
     * 种植菌类
     */
    @JSONField(ordinal = 3)
    @TableField
    private Integer seedType;

    @JSONField(deserialize = false, ordinal = 4)
    @TableField(exist = false)
    private String seedTypeName;

    /**
     * 利率
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 5)
    @TableField
    private BigDecimal interestRates;


    /**
     * 土地等级
     */
    @JSONField(ordinal = 6)
    @TableField
    private Integer level;

    /**
     * 总额度
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 7)
    @TableField
    private BigDecimal totalAmount;

    /**
     * 单价
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 8)
    @TableField
    private BigDecimal perPrice;

    /**
     * 总份数
     */
    @JSONField(ordinal = 9)
    @TableField
    private Integer totalCount;

    /**
     * 剩余额度
     */
    @JSONField(ordinal = 10)
    @TableField
    private Integer leftCount;

    /**
     * 已用额度
     */
    @JSONField(ordinal = 11)
    @TableField
    private Integer hasCount;

    /**
     * 是否显示[0不显示，1已显示]
     */
    @JSONField(ordinal = 12)
    @TableField
    private Boolean isShow;

    /**
     * 状态[0未使用，1使用中]
     */
    @JSONField(ordinal = 13)
    @TableField
    private Integer state;

    /**
     * 添加时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 14)
    @TableField
    private Date addTime;

    /**
     * 是否删除[0否，1是]
     */
    @JSONField(ordinal = 15)
    @TableField
    private Boolean isDelete;

    /**
     * 棚内培育菌包的生长周期
     */
    @JSONField(ordinal = 16)
    @TableField
    private Integer growDays;

    /**
     * 最高种植份数
     */
    @JSONField(ordinal = 17)
    @TableField
    private Integer maxPlant;

    /**
     * 最低种植份数
     */
    @JSONField(ordinal = 18)
    @TableField
    private Integer minPlant;

    /**
     * 图片
     */
    @JSONField(ordinal = 19)
    @TableField
    private String picUrl;

    /**
     * 缩略图
     */
    @JSONField(ordinal = 20)
    @TableField
    private String thumbPicUrl;

}