package com.moguying.plant.core.entity.payment.request;


import lombok.Data;

@Data
public class WithdrawMoneyPageRequest implements PaymentRequestInterface {

    /**
     * 提现商户号
     */
    private String wdMerNo;

    /**
     * 提现商户号
     */
    private String merMerOrderNo;

    /**
     * 平台承担手续费百分比.0-平台不承担，1-平台全部
     * 0-1之间为平台承担部分
     */
    private String ptUndertakeRate;

    /**
     * 代平台扣手续费。填入平台商户号和代扣金额，
     * [{"merNo":"0000143","withHoldFee":"0.1"}]
     */
    private String ptWithholdFeeRoutingList;

}
