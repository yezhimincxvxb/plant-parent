package com.moguying.plant.core.entity.seed.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class SendPayOrder implements Serializable {


    @JSONField(ordinal = 1)
    private Boolean isCheck;

    @JSONField(serialize = false)
    private String payPassword;

    @JSONField(serialize = false)
    private String payMsgCode;

    @JSONField(serialize = false)
    private String seqNo;

    @JSONField(ordinal = 2)
    private Integer orderId;

    @JSONField(serialize = false)
    private String authMsg;

    @JSONField(serialize = false)
    private Integer bankId;

    @JSONField(serialize = false)
    private List<Integer> fertilizerIds;

    public String getAuthMsg() {
        return authMsg;
    }

    public void setAuthMsg(String authMsg) {
        this.authMsg = authMsg;
    }

    public Boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Boolean check) {
        isCheck = check;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getPayMsgCode() {
        return payMsgCode;
    }

    public void setPayMsgCode(String payMsgCode) {
        this.payMsgCode = payMsgCode;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }


    public List<Integer> getFertilizerIds() {
        return fertilizerIds;
    }

    public void setFertilizerIds(List<Integer> fertilizerIds) {
        this.fertilizerIds = fertilizerIds;
    }

    @Override
    public String toString() {
        return "SendPayOrder{" +
                "isCheck=" + isCheck +
                ", payPassword='" + payPassword + '\'' +
                ", payMsgCode='" + payMsgCode + '\'' +
                ", seqNo='" + seqNo + '\'' +
                ", orderId=" + orderId +
                ", authMsg='" + authMsg + '\'' +
                ", bankId=" + bankId +
                ", fertilizerIds=" + fertilizerIds +
                '}';
    }
}
