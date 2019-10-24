package com.moguying.plant.core.entity.payment.callback;

import lombok.Data;

@Data
public class BindCardCallback {

    private String orderNo;

    private String cardStyle;

    private String bankAbbr;

    private String bankName;
}
