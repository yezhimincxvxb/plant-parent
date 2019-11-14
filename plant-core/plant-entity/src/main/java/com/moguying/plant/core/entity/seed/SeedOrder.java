package com.moguying.plant.core.entity.seed;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_seed_order
 * @author 
 */
@TableName("plant_seed_order")
@Data
@Accessors(chain = true)
public class SeedOrder implements Serializable {

    private static final long serialVersionUID = 43019283648714030L;


    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 菌包类型
     */
    @JSONField(ordinal = 2)
    @TableField
    private Integer seedType;

    /**
     * 菌包类型名称
     */
    @Excel(name = "菌包类型")
    @JSONField(ordinal = 3)
    @TableField(exist = false)
    private String seedTypeName;

    /**
     * 用户id
     */
    @JSONField(ordinal = 4)
    @TableField
    private Integer userId;


    /**
     * 用户名
     */
    @Excel(name = "用户名")
    @JSONField(ordinal = 5)
    @TableField(exist = false)
    private String phone;


    /**
     * 购买总份数
     */
    @Excel(name = "购买总份数")
    @JSONField(ordinal = 6)
    @TableField
    private Integer buyCount;

    /**
     * 购买总价
     */
    @Excel(name = "购买总价")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    @TableField
    private BigDecimal buyAmount;

    /**
     * 种植份数
     */
    @Excel(name = "种植份数")
    @JSONField(ordinal = 8)
    @TableField
    private Integer plantCount;


    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    @JSONField(ordinal = 9)
    @TableField(exist = false)
    private String realName;


    /**
     * 是否新手
     */
    @JSONField(serialize = false)
    @TableField(exist = false)
    private Boolean isForNew;


}