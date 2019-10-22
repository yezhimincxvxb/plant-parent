package com.moguying.plant.core.entity.dto.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class WebHtmlPayRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String sellerNo;

    @JSONField(ordinal = 2)
    private String payChannels;

    @JSONField(ordinal = 3)
    private String authCodes = "";

    @JSONField(ordinal = 4)
    private String orderBody;

    @JSONField(ordinal = 5)
    private String payAmount;

    @JSONField(ordinal = 6)
    private String apiPayType = "1";

    @JSONField(ordinal = 7)
    private String tradeType = "0";

    @JSONField(ordinal = 8)
    private String merMerOrderNo;

    @JSONField(ordinal = 9)
    private String buyerNo;

    @JSONField(ordinal = 10)
    private String undiscountableAmount = "";

    @JSONField(ordinal = 11)
    private String orderSubject;

    @JSONField(ordinal = 12)
    private String tempRoutingList = "";

    @JSONField(ordinal = 13)
    private String returnUrl;

    @JSONField(ordinal = 14)
    private String ptUndertakeRate = "";


    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getPayChannels() {
        return payChannels;
    }

    public void setPayChannels(String payChannels) {
        this.payChannels = payChannels;
    }

    public String getAuthCodes() {
        return authCodes;
    }

    public void setAuthCodes(String authCodes) {
        this.authCodes = authCodes;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getApiPayType() {
        return apiPayType;
    }

    public void setApiPayType(String apiPayType) {
        this.apiPayType = apiPayType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMerMerOrderNo() {
        return merMerOrderNo;
    }

    public void setMerMerOrderNo(String merMerOrderNo) {
        this.merMerOrderNo = merMerOrderNo;
    }

    public String getBuyerNo() {
        return buyerNo;
    }

    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public String getUndiscountableAmount() {
        return undiscountableAmount;
    }

    public void setUndiscountableAmount(String undiscountableAmount) {
        this.undiscountableAmount = undiscountableAmount;
    }

    public String getOrderSubject() {
        return orderSubject;
    }

    public void setOrderSubject(String orderSubject) {
        this.orderSubject = orderSubject;
    }

    public String getTempRoutingList() {
        return tempRoutingList;
    }

    public void setTempRoutingList(String tempRoutingList) {
        this.tempRoutingList = tempRoutingList;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getPtUndertakeRate() {
        return ptUndertakeRate;
    }

    public void setPtUndertakeRate(String ptUndertakeRate) {
        this.ptUndertakeRate = ptUndertakeRate;
    }
}
