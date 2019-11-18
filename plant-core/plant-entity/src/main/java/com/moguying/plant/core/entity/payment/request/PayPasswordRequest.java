package com.moguying.plant.core.entity.payment.request;

import lombok.Data;

@Data
public class PayPasswordRequest implements PaymentRequestInterface {

    private String merchantNo;
}
