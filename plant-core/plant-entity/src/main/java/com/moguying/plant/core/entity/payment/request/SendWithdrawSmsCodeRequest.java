package com.moguying.plant.core.entity.payment.request;

import lombok.Data;

@Data
public class SendWithdrawSmsCodeRequest implements PaymentRequestInterface {

    /**
     * 提现商户号
     */
    protected String wdMerNo;

    /**
     * API商户订单号
     */
    protected String merMerOrderNo;

    /**
     * 平台承担手续费百分比。0-平台不承担，1-平台全部承担。0-1之间为平台承担部分。
     */
    protected String ptUndertakeRate = "";

    /**
     * 代平台扣手续费。填入平台商户号和代扣金额，[{"merNo":"0000143","withHoldFee":"0.1"}]
     */
    protected String ptWithholdFeeRoutingList = "";

    /**
     * 提现金额
     */
    protected String amount;

    /**
     * 提现银行卡号
     */
    protected String cardNo;

    /**
     * 银行卡预留手机号
     */
    protected String phone;

    /**
     * 银行卡预留手机号
     */
    protected String cardType = "";
}
