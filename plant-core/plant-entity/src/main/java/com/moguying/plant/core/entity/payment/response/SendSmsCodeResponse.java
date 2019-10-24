package com.moguying.plant.core.entity.payment.response;

public class SendSmsCodeResponse implements PaymentResponseInterface {

    private String seqNo;

    private String bkMerNo;

    private String date;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBkMerNo() {
        return bkMerNo;
    }

    public void setBkMerNo(String bkMerNo) {
        this.bkMerNo = bkMerNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
