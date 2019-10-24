package com.moguying.plant.core.entity.account;

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

@Data
@TableName("plant_money_recharge")
public class MoneyRecharge implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @JSONField(name = "user_id")
    @TableField
    private Integer userId;

    @JSONField(name = "user_name",deserialize = false)
    @TableField(exist = false)
    private String userName;

    @JSONField(name = "order_number")
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
    @JSONField(name = "recharge_time",format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date rechargeTime;

    /**
     * 到账时间
     */
    @JSONField(name = "to_account_time",format = "yyyy-MM-dd HH:mm:ss")
    @TableField
    private Date toAccountTime;

    /**
     * 到账金额
     */
    @JSONField(name = "to_account_money",serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal toAccountMoney;

    /**
     * 充值手续费
     */
    @JSONField(name = "fee",serializeUsing = BigDecimalSerialize.class)
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

}