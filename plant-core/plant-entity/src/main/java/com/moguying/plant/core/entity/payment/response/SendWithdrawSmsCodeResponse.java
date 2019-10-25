package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class SendWithdrawSmsCodeResponse implements PaymentResponseInterface {

    /**
     * API商户订单号
     */
    private String merMerOrderNo;

    /**
     * 交易流水号
     */
    private String seqNo;

}
