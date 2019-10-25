package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class WithdrawMoneyResponse implements PaymentResponseInterface {

    /**
     * API商户订单号
     */
    private String merMerOrderNo;

    /**
     * 双乾订单号
     */
    private String orderNo;

    /**
     * 提现金额
     */
    private String amount;
}
