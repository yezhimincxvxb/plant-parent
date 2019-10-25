package com.moguying.plant.core.entity.payment.response;

import lombok.Data;

@Data
public class RealnameSyncResponse implements PaymentResponseInterface {

    private String type;

    private String sonMerNo;

    private String legalPersonName;

}
