package com.moguying.plant.core.entity.dto.payment.request;

import com.alibaba.fastjson.annotation.JSONField;

public class DeleteBankCardRequest implements PaymentRequestInterface {

    @JSONField(ordinal = 1)
    private String bkMerNo;

    @JSONField(ordinal = 2)
    private String cardNo;

    @JSONField(ordinal = 3)
    private String seqNo;

    @JSONField(ordinal = 4)
    private String bindCardQyfOrUnion = "1";


    public String getBkMerNo() {
        return bkMerNo;
    }

    public void setBkMerNo(String bkMerNo) {
        this.bkMerNo = bkMerNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getBindCardQyfOrUnion() {
        return bindCardQyfOrUnion;
    }

    public void setBindCardQyfOrUnion(String bindCardQyfOrUnion) {
        this.bindCardQyfOrUnion = bindCardQyfOrUnion;
    }
}
