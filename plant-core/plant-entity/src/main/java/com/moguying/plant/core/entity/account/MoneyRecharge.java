package com.moguying.plant.core.entity.account;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.moguying.plant.utils.BigDecimalSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("plant_money_recharge")
public class MoneyRecharge implements Serializable {

    private static final long serialVersionUID = 7600188971919377229L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField
    private Integer userId;



    @TableField(exist = false)
    private String phone;

    @TableField(exist = false)
    private String realName;

    @TableField
    private String orderNumber;

    /**
     * 充值金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal money;

    /**
     * 充值订单状态[0充值中，1充值成功，2充值失败]
     */
    @TableField
    private Integer state;

    /**
     * 充值时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date rechargeTime;

    /**
     * 到账时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date toAccountTime;

    /**
     * 到账金额
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal toAccountMoney;

    /**
     * 充值手续费
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal fee;


    /**
     * 第三方支付短信流水号
     */
    @JSONField(serialize = false,deserialize = false)
    @TableField
    private String paySmsSqNo;


    /**
     * 充值来源
     */
    @TableField
    private String source;

    /**
     * 操作人ID
     */
    @TableField(exist = false)
    private Integer reviewId;

    /**
     * 操作人姓名
     */
    @TableField(exist = false)
    private String reviewName;

}