package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class InviteStatistics implements Serializable {

    @JSONField(ordinal = 1)
    private Integer inviteCount;

    @JSONField(ordinal = 2, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal invitePlantAmount;

    @JSONField(ordinal = 3, serializeUsing = BigDecimalSerialize.class)
    private BigDecimal inviteAward;

    @JSONField(ordinal = 4)
    private String phone;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(Integer inviteCount) {
        this.inviteCount = inviteCount;
    }

    public BigDecimal getInvitePlantAmount() {
        return invitePlantAmount;
    }

    public void setInvitePlantAmount(BigDecimal invitePlantAmount) {
        this.invitePlantAmount = invitePlantAmount;
    }

    public BigDecimal getInviteAward() {
        return inviteAward;
    }

    public void setInviteAward(BigDecimal inviteAward) {
        this.inviteAward = inviteAward;
    }
}
