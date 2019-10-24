package com.moguying.plant.core.entity.payment;

import java.io.Serializable;
import java.util.Date;

/**
 * plant_payment_info
 * @author 
 */
public class PaymentInfo implements Serializable {
    private Integer id;

    /**
     * 请求方法
     */
    private String requestAction;

    /**
     * 请求参数
     */
    private String paymentRequest;

    /**
     * 请求响应
     */
    private String paymentResponse;

    /**
     * 异步请求响应
     */
    private String notifyResponse;

    /**
     * 签名数据
     */
    private String signData;

    /**
     * 处理状态[1已处理，0未处理]
     */
    private Integer state;

    /**
     * 支付流水号
     */
    private String orderNumber;

    /**
     * 添加时间
     */
    private Date addTime;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestAction() {
        return requestAction;
    }

    public void setRequestAction(String requestAction) {
        this.requestAction = requestAction;
    }

    public String getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(String paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public String getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(String paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNotifyResponse() {
        return notifyResponse;
    }

    public void setNotifyResponse(String notifyResponse) {
        this.notifyResponse = notifyResponse;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}