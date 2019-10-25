package com.moguying.plant.core.entity.payment.response;


import lombok.Data;

@Data
public class SendPaySmsCodeResponse implements PaymentResponseInterface {
    private String merOrderNo;


    private String seqNo;

}
