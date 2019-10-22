package com.moguying.plant.core.entity.dto.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class BindCardRequest implements PaymentRequestInterface{

    @JSONField(ordinal = 1)
    private String seqNo;

    @JSONField(ordinal = 2)
    private String bkMerNo;

    @JSONField(ordinal = 3)
    private String cardNo;

    @JSONField(ordinal = 4)
    private String account = "";

    @JSONField(ordinal = 5)
    private String smsCode;

    public String getAccount() {
        return account;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBkMerNo() {
        return bkMerNo;
    }

    public void setBkMerNo(String bkMerNo) {
        this.bkMerNo = bkMerNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
