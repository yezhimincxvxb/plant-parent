package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.IdCardSerialize;

import java.io.Serializable;

public class BindCard implements Serializable {

    @JSONField(ordinal = 1,serializeUsing = IdCardSerialize.class)
    private String bankNumber;

    @JSONField(serialize = false)
    private String msgCode;

    @JSONField(serialize = false)
    private String seqNo;

    @JSONField(ordinal = 2,serializeUsing = IdCardSerialize.class)
    private String phone;

    @JSONField(ordinal = 3,deserialize = false)
    private Boolean state;

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }
}
