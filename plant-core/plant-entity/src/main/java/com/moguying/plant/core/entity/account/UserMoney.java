package com.moguying.plant.core.entity.account;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_user_money
 * @author
 */
@TableName("plant_user_money")
@Data
@NoArgsConstructor
public class UserMoney implements Serializable {

    private static final long serialVersionUID = -811523716458008411L;
    /**
     * 用户id
     */
    @Excel(name = "用户ID",orderNum = "0")
    @JSONField(ordinal = 1)
    @TableId
    private Integer userId;

    @Excel(name = "手机号",orderNum = "1")
    @JSONField(ordinal = 2)
    @TableField(exist = false)
    private String phone;

    @Excel(name = "姓名",orderNum = "2")
    @JSONField(ordinal = 3)
    @TableField(exist = false)
    private String realName;

    /**
     * 可用金额
     */
    @Excel(name = "可用金额",orderNum = "3")
    @JSONField( serializeUsing = BigDecimalSerialize.class,ordinal = 4)
    @TableField
    private BigDecimal availableMoney;

    /**
     * 冻结金额
     */
    @Excel(name = "冻结金额",orderNum = "4")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 5)
    @TableField
    private BigDecimal freezeMoney;

    /**
     * 待收金额
     */
    @Excel(name = "待收金额",orderNum = "5")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 6)
    @TableField
    private BigDecimal collectMoney;

    /**
     * 待收本金
     */
    @Excel(name = "待收本金",orderNum = "6")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 7)
    @TableField
    private BigDecimal collectCapital;

    /**
     * 待收利息
     */
    @Excel(name = "待收利息",orderNum = "7")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 8)
    @TableField
    private BigDecimal collectInterest;


    @Excel(name = "账户总额",orderNum = "8")
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 9)
    @TableField(exist = false)
    private BigDecimal totalAmount;

    public UserMoney(Integer userId) {
        this.userId = userId;
        this.availableMoney = new BigDecimal("0.00");
        this.freezeMoney = new BigDecimal("0.00");
        this.collectMoney = new BigDecimal("0.00");
        this.collectCapital = new BigDecimal("0.00");
        this.collectInterest = new BigDecimal("0.00");
    }
}