package com.moguying.plant.core.entity.payment.response;


import lombok.Data;

@Data
public class RegisterSyncResponse implements PaymentResponseInterface{
    private String customerNo;

    private String merchantNo;

}
