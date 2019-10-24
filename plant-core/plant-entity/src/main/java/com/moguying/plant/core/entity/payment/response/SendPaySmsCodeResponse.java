package com.moguying.plant.core.entity.payment.response;

public class SendPaySmsCodeResponse implements PaymentResponseInterface {
    private String merOrderNo;


    private String seqNo;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getMerOrderNo() {
        return merOrderNo;
    }

    public void setMerOrderNo(String merOrderNo) {
        this.merOrderNo = merOrderNo;
    }
}
