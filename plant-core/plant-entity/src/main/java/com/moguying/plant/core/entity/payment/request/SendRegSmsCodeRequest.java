package com.moguying.plant.core.entity.payment.request;


import lombok.Data;

/**
 * 快捷注册发送短信
 */
@Data
public class SendRegSmsCodeRequest implements PaymentRequestInterface{

    private String merType;

    private String custName;

    private String idNo;

    private String phone;

    private String merchantName = "";

    private String customerNo;

}
