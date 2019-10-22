package com.moguying.plant.core.entity.dto.payment.response;

public class SendWithdrawSmsCodeResponse implements PaymentResponseInterface {

    /**
     * API商户订单号
     */
    private String merMerOrderNo;

    /**
     * 交易流水号
     */
    private String seqNo;


    public String getMerMerOrderNo() {
        return merMerOrderNo;
    }

    public void setMerMerOrderNo(String merMerOrderNo) {
        this.merMerOrderNo = merMerOrderNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
}
