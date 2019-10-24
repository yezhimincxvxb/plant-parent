package com.moguying.plant.core.entity.payment.callback;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.payment.response.PaymentResponseInterface;

/**
 * 第三方主动通知的请求参数
 */
public class CallBackResponse implements PaymentResponseInterface {

    @JSONField(ordinal = 1)
    private String merMerOrderNo;

    @JSONField(ordinal = 2)
    private String payAmount;

    @JSONField(ordinal = 3)
    private String orderNo;

    @JSONField(ordinal = 4)
    private String tradeFee;

    @JSONField(ordinal = 5)
    private String sellerNo;

    @JSONField(ordinal = 6)
    private String buyerNo;

    @JSONField(ordinal = 7)
    private String channelType;

    public String getMerMerOrderNo() {
        return merMerOrderNo;
    }

    public void setMerMerOrderNo(String merMerOrderNo) {
        this.merMerOrderNo = merMerOrderNo;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(String tradeFee) {
        this.tradeFee = tradeFee;
    }

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getBuyerNo() {
        return buyerNo;
    }

    public void setBuyerNo(String buyerNo) {
        this.buyerNo = buyerNo;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
