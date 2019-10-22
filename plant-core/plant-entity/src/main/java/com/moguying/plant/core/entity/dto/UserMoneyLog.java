package com.moguying.plant.core.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.common.util.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_user_money_log
 * @author 
 */
@Data
public class UserMoneyLog implements Serializable {

    @Excel(name = "序号")
    private Long id;

    /**
     * 用户id 
     */
    @JSONField(ordinal = 1)
    private Integer userId;

    @Excel(name = "用户名")
    @JSONField(ordinal = 2)
    private String phone;


    /**
     * 影响金额
     */
    @Excel(name = "操作金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 3)
    private BigDecimal affectMoney;

    /**
     * 可用金额
     */
    @Excel(name = "可用金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 4)
    private BigDecimal availableMoney;

    /**
     * 冻结金额
     */
    @Excel(name = "冻结金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 5)
    private BigDecimal freezeMoney;

    /**
     * 待收金额
     */
    @Excel(name = "待收金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 6)
    private BigDecimal collectMoney;

    /**
     * 待收本金
     */
    @Excel(name = "待收本金")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    private BigDecimal collectCapital;

    /**
     * 待收利息
     */
    @Excel(name = "待收利息")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 8)
    private BigDecimal collectInterest;

    /**
     * 操作类型
     */

    @JSONField(ordinal = 9)
    private Byte affectType;

    /**
     * 操作时间
     */
    @Excel(name = "操作时间",format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss:SSS",ordinal = 10)
    private Date affectTime;


    /**
     * 操作ip
     */
    private transient String affectIp;

    /**
     * 操作对应的详细id
     */
    @Excel(name = "操作流水号")
    @JSONField(ordinal = 11)
    private String detailId;

    /**
     * 操作备注
     */
    @Excel(name = "操作备注")
    @JSONField(ordinal = 12)
    private String affectInfo;

    /**
     * 查询辅助
     */
    @JSONField(serialize = false)
    private Date startTime;

    /**
     * 查询辅助
     */
    @JSONField(serialize = false)
    private Date endTime;

    @Excel(name = "真实姓名")
    private String realName;
}