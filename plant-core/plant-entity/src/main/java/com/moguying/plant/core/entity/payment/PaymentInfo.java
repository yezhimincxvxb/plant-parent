package com.moguying.plant.core.entity.payment;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = -7330965500749750087L;

    private String id;

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
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    // 辅助字段
    private String userName;
    private Date start;
    private Date end;


}