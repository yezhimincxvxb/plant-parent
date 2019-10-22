package com.moguying.plant.core.entity.dto.payment.request;

public class WithdrawMoneyRequest extends SendWithdrawSmsCodeRequest {

    private String seqNo;

    private String smsCode;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
