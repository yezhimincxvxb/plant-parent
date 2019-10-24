package com.moguying.plant.core.entity.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class PaymentRequestForHtml {

    @JSONField(ordinal = 1)
    private String merNo;

    @JSONField(ordinal = 2)
    private String version;

    @JSONField(ordinal = 3)
    private String notifyUrl;

    @JSONField(ordinal = 4)
    private String timestamp;

    @JSONField(ordinal = 5)
    private String apiContent;

    @JSONField(ordinal = 6)
    private String signType;

    @JSONField(ordinal = 7)
    private String sign;

    @JSONField(ordinal = 8)
    private String actionUrl;

    public String getMerNo() {
        return merNo;
    }

    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getApiContent() {
        return apiContent;
    }

    public void setApiContent(String apiContent) {
        this.apiContent = apiContent;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
