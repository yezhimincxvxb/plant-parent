package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class DeleteBankCardResponse implements PaymentResponseInterface {

    private String seqNo;

    private String bkMerNo;
}
