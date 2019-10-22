package com.moguying.plant.core.entity.vo;

public class WeChatShare {

    public WeChatShare() {
    }

    public WeChatShare(String timestamp, String noncestr, String signature) {
        this.timestamp = timestamp;
        this.noncestr = noncestr;
        this.signature = signature;
    }

    private String timestamp;

    private String noncestr;

    private String signature;

    private String url;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
