package com.moguying.plant.core.entity.payment.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class QueryBankCardBinRequest implements PaymentRequestInterface {

    @Setter(value = AccessLevel.PRIVATE)
    private String type = "0";

    private String cardNo;
}
