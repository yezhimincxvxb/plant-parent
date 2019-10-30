package com.moguying.plant.core.entity.seed.vo;

import lombok.Data;

@Data
public class SendPayOrderResponse {

    private Boolean needPassword;

    private String seqNo;

    private String errorMsg;
}
