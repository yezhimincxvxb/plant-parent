package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class RegisterAsyncResponse implements PaymentResponseInterface {

    private String merType;

    private String merNo;

    private String sonMerNo;

    private String status;

    private String seraialNumber;
}
