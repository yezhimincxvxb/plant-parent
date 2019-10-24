package com.moguying.plant.core.entity.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;
import com.moguying.plant.utils.IdCardSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * plant_user_invite
 * @author 
 */
public class UserInvite implements Serializable {

    @JSONField(ordinal = 1)
    private Integer userId;

    @JSONField(ordinal = 2,serializeUsing = IdCardSerialize.class)
    private String phone;

    /**
     * 种植金额
     */

    @JSONField(ordinal = 3,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantAmount;

    /**
     * 邀请奖励金额
     */
    @JSONField(ordinal = 4,serializeUsing = BigDecimalSerialize.class)
    private BigDecimal inviteAward;

    /**
     * 邀请人id
     */
    @JSONField(serialize = false,deserialize = false)
    private Integer inviteUserId;

    /**
     * 注册时间
     */
    @JSONField(ordinal = 5,format = "yyyy.MM.dd")
    private Date regTime;


    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getPlantAmount() {
        return plantAmount;
    }

    public void setPlantAmount(BigDecimal plantAmount) {
        this.plantAmount = plantAmount;
    }

    public BigDecimal getInviteAward() {
        return inviteAward;
    }

    public void setInviteAward(BigDecimal inviteAward) {
        this.inviteAward = inviteAward;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }
}