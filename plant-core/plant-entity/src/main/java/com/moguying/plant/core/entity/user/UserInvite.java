package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.IdCardSerialize;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_user_invite
 *
 * @author
 */
@TableName("plant_user_invite")
@Data
public class UserInvite implements Serializable {

    private static final long serialVersionUID = -84854519965079631L;

    @JSONField(ordinal = 1)
    @TableId
    private Integer userId;

    @JSONField(ordinal = 2, serializeUsing = IdCardSerialize.class)
    @TableField
    private String phone;

    /**
     * 种植金额
     */

    @JSONField(ordinal = 3, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal plantAmount;

    /**
     * 邀请奖励金额
     */
    @JSONField(ordinal = 4, serializeUsing = BigDecimalSerialize.class)
    @TableField
    private BigDecimal inviteAward;

    /**
     * 邀请人id
     */
    @JSONField(serialize = false, deserialize = false)
    @TableField
    private Integer inviteUserId;

    /**
     * 注册时间
     */
    @JSONField(ordinal = 5, format = "yyyy.MM.dd")
    @TableField(exist = false)
    private Date regTime;

}