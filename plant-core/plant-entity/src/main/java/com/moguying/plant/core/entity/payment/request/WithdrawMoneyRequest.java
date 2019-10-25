package com.moguying.plant.core.entity.payment.request;


import lombok.Data;

@Data
public class WithdrawMoneyRequest extends SendWithdrawSmsCodeRequest {

    private String seqNo;

    private String smsCode;
}
