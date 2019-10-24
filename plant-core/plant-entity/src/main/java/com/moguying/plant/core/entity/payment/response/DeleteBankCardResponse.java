package com.moguying.plant.core.entity.payment.response;

public class DeleteBankCardResponse implements PaymentResponseInterface {

    private String seqNo;

    private String bkMerNo;

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
}
