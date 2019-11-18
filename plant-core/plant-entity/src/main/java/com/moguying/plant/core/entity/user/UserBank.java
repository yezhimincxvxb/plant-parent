package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BankCarSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@TableName("plant_user_bank")
@Data
public class UserBank implements Serializable {

    private static final long serialVersionUID = 1326991723118596309L;

    @JSONField(ordinal = 1)
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @JSONField(ordinal = 2, serialize = false)
    @TableField
    private Integer userId;

    /**
     * 银行卡号
     */
    @JSONField(serializeUsing = BankCarSerialize.class, ordinal = 3)
    @TableField
    private String bankNumber;

    /**
     * 流水号
     */
    @JSONField(ordinal = 4)
    @TableField
    private String orderNumber;

    /**
     * 所属银行代码
     */
    @JSONField(ordinal = 5)
    @TableField
    private String bankId;

    /**
     * 银行卡类型
     */
    @JSONField(ordinal = 6)
    @TableField
    private String cardType;

    /**
     * 银行名称
     */
    @JSONField(ordinal = 7, name = "bankName")
    @TableField
    private String bankAddress;

    /**
     * 绑卡时间
     */
    @JSONField(serialize = false, deserialize = false, ordinal = 8)
    @TableField
    private Date addTime;

    /**
     * 卡片状态[0使用中，1已停止]
     */
    @JSONField(ordinal = 9)
    @TableField
    private Integer state;

    @JSONField(serialize = false, deserialize = false, ordinal = 10)
    @TableField(exist = false)
    private String paymentAccount;

    @JSONField(ordinal = 11)
    @TableField
    private String bankPhone;


}