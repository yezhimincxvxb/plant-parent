package com.moguying.plant.core.entity.fertilizer;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_user_fertilizer
 * @author 
 */
@Data
public class UserFertilizer implements Serializable {
    /**
     * id
     */
    @JSONField(ordinal = 1)
    private Integer id;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    @JSONField(ordinal = 2)
    private Integer userId;

    /**
     * 券id
     */
    @JSONField(ordinal = 3)
    private Integer fertilizerId;

    /**
     * [0未使用，1已使用，2已过期]
     */
    @Excel(name = "状态",replace = {"未使用_0","已使用_1","已过期_2"},orderNum = "1")
    @JSONField(ordinal = 4)
    private Integer state;

    /**
     * 使用对应的流水号
     */
    @JSONField(ordinal = 5)
    @Excel(name = "使用详细流水",orderNum = "2")
    private String useOrderNumber;

    /**
     * 添加时间
     */
    @Excel(name = "添加时间",orderNum = "3",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 6)
    private Date addTime;

    /**
     * 券有效时间
     */
    @Excel(name = "有效时间",orderNum = "4",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 7)
    private Date startTime;

    /**
     * 券失效时间
     */
    @Excel(name = "失效时间",orderNum = "5",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",ordinal = 8)
    private Date endTime;

    /**
     * 券
     */
    @Excel(name = "券",orderNum = "6")
    @JSONField(ordinal = 9)
    private Fertilizer fertilizer;


    /**
     * 手机号
     */
    @Excel(name = "手机号",orderNum = "7")
    @JSONField(ordinal = 10)
    private String phone;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名",orderNum = "8")
    @JSONField(ordinal = 11)
    private String realName;

    /**
     * 券金额
     */
    @JSONField(ordinal = 12,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal fertilizerAmount;
}