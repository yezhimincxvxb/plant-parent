package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class TransferResponse implements PaymentResponseInterface {

    private String payeeNo;

    private String amount;

    private String cardNo;


}
