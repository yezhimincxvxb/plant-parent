package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class SendPaySmsCodeRequest implements PaymentRequestInterface {

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

    @JSONField(ordinal = 7)
    private String orderSubject;

    @JSONField(ordinal = 8)
    private String orderBody = "";

    @JSONField(ordinal = 9)
    private String apiPayType;

    @JSONField(ordinal = 10)
    private String tempRoutingList = "";

    @JSONField(ordinal = 11)
    private String ptUndertakeRate = "";

    @JSONField(ordinal = 12)
    private String sellerNo;


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

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public String getApiPayType() {
        return apiPayType;
    }

    public void setApiPayType(String apiPayType) {
        this.apiPayType = apiPayType;
    }

    public String getTempRoutingList() {
        return tempRoutingList;
    }

    public void setTempRoutingList(String tempRoutingList) {
        this.tempRoutingList = tempRoutingList;
    }

    public String getPtUndertakeRate() {
        return ptUndertakeRate;
    }

    public void setPtUndertakeRate(String ptUndertakeRate) {
        this.ptUndertakeRate = ptUndertakeRate;
    }

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }
}
