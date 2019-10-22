package com.moguying.plant.core.entity.dto.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class PaySignQueryRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String cardNo;

    @JSONField(ordinal = 2)
    private String custName;

    @JSONField(ordinal = 3)
    private String phone;

    @JSONField(ordinal = 4)
    private String idNo;

    @JSONField(ordinal = 5)
    private String payAmount;

    @JSONField(ordinal = 6)
    private String merOrderNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
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

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getMerOrderNo() {
        return merOrderNo;
    }

    public void setMerOrderNo(String merOrderNo) {
        this.merOrderNo = merOrderNo;
    }
}
