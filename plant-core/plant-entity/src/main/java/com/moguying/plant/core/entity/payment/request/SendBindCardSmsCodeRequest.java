package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class SendBindCardSmsCodeRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String bkMerNo;

    //个人银行卡
    @JSONField(ordinal = 2)
    private String cardType = "0";

    @JSONField(ordinal = 3)
    private String bankNo = "";

    @JSONField(ordinal = 4)
    private String cardNo;

    @JSONField(ordinal = 5)
    private String merMerOrderNo = "";

    @JSONField(ordinal = 6)
    private String phone;

    @JSONField(ordinal = 7)
    private String idNo;

    @JSONField(ordinal = 8)
    private String custName;


    public String getCardType() {
        return cardType;
    }

    public String getBankNo() {
        return bankNo;
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

    public String getMerMerOrderNo() {
        return merMerOrderNo;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
