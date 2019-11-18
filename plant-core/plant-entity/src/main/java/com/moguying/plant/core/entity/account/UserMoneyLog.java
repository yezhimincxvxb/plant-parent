package com.moguying.plant.core.entity.account;

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

/**
 * plant_user_money_log
 *
 * @author
 */
@TableName("plant_user_money_log")
@Data
public class UserMoneyLog implements Serializable {

    private static final long serialVersionUID = -4177944900186120746L;

    @Excel(name = "序号")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @JSONField(ordinal = 1)
    @TableField
    private Integer userId;

    @Excel(name = "用户名")
    @JSONField(ordinal = 2)
    @TableField(exist = false)
    private String phone;


    /**
     * 影响金额
     */
    @Excel(name = "操作金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 3)
    @TableField
    private BigDecimal affectMoney;

    /**
     * 可用金额
     */
    @Excel(name = "可用金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 4)
    @TableField
    private BigDecimal availableMoney;

    /**
     * 冻结金额
     */
    @Excel(name = "冻结金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 5)
    @TableField
    private BigDecimal freezeMoney;

    /**
     * 待收金额
     */
    @Excel(name = "待收金额")
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 6)
    @TableField
    private BigDecimal collectMoney;

    /**
     * 待收本金
     */
    @Excel(name = "待收本金")
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 7)
    @TableField
    private BigDecimal collectCapital;

    /**
     * 待收利息
     */
    @Excel(name = "待收利息")
    @JSONField(serializeUsing = BigDecimalSerialize.class, ordinal = 8)
    @TableField
    private BigDecimal collectInterest;

    /**
     * 操作类型
     */

    @JSONField(ordinal = 9)
    @TableField
    private Byte affectType;

    /**
     * 操作时间
     */
    @Excel(name = "操作时间", format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss:SSS", ordinal = 10)
    @TableField
    private Date affectTime;


    /**
     * 操作ip
     */
    @TableField
    private transient String affectIp;

    /**
     * 操作对应的详细id
     */
    @Excel(name = "操作流水号")
    @JSONField(ordinal = 11)
    @TableField
    private String detailId;

    /**
     * 操作备注
     */
    @Excel(name = "操作备注")
    @JSONField(ordinal = 12)
    @TableField
    private String affectInfo;

    /**
     * 查询辅助
     */
    @JSONField(serialize = false)
    @TableField(exist = false)
    private Date startTime;

    /**
     * 查询辅助
     */
    @JSONField(serialize = false)
    @TableField(exist = false)
    private Date endTime;

    @Excel(name = "真实姓名")
    @TableField(exist = false)
    private String realName;
}