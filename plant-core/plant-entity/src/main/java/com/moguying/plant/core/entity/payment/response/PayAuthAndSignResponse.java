package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class PayAuthAndSignResponse implements PaymentResponseInterface {
    private String merOrderNo;
}
