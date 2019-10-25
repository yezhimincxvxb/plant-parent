package com.moguying.plant.core.entity.payment.response;


import lombok.Data;

@Data
public class SendSmsCodeResponse implements PaymentResponseInterface {

    private String seqNo;

    private String bkMerNo;

    private String date;


}
