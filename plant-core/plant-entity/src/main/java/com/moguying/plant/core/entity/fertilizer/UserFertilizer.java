package com.moguying.plant.core.entity.fertilizer;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
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
 * plant_user_fertilizer
 *
 * @author
 */
@Data
@TableName("plant_user_fertilizer")
public class UserFertilizer implements Serializable {

    private static final long serialVersionUID = -620902626205027459L;
    /**
     * id
     */
    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    @JSONField(ordinal = 2)
    @TableField
    private Integer userId;

    /**
     * 券id
     */
    @Excel(name = "券id")
    @JSONField(ordinal = 3)
    @TableField
    private Integer fertilizerId;

    /**
     * 券金额
     */
    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal fertilizerAmount;

    /**
     * [0未使用，1已使用，2已过期]
     */
    @Excel(name = "状态", replace = {"未使用_0", "已使用_1", "已过期_2"}, orderNum = "1")
    @JSONField(ordinal = 5)
    @TableField
    private Integer state;

    /**
     * 使用对应的流水号
     */
    @JSONField(ordinal = 6)
    @Excel(name = "使用详细流水", orderNum = "2")
    @TableField
    private String useOrderNumber;

    /**
     * 添加时间
     */
    @Excel(name = "添加时间", orderNum = "3", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 7)
    @TableField
    private Date addTime;

    /**
     * 券有效时间
     */
    @Excel(name = "有效时间", orderNum = "4", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 8)
    @TableField
    private Date startTime;

    /**
     * 券失效时间
     */
    @Excel(name = "失效时间", orderNum = "5", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 9)
    @TableField
    private Date endTime;

    /**
     * 券
     */
    @ExcelEntity
    @JSONField(ordinal = 10)
    @TableField(exist = false)
    private Fertilizer fertilizer;


    /**
     * 手机号
     */
    @Excel(name = "手机号", orderNum = "7")
    @JSONField(ordinal = 11)
    @TableField(exist = false)
    private String phone;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名", orderNum = "8")
    @JSONField(ordinal = 12)
    @TableField(exist = false)
    private String realName;

    /**
     * 券使用时间
     */
    @Excel(name = "券使用时间", orderNum = "9", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 13)
    @TableField
    private Date useTime;

}