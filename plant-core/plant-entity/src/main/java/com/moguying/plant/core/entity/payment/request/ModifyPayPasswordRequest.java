package com.moguying.plant.core.entity.payment.request;

import lombok.Data;

@Data
public class ModifyPayPasswordRequest implements PaymentRequestInterface {

    private String merchantNo;

    private String type;

}
