package com.moguying.plant.core.entity.common.vo;

import lombok.Data;

@Data
public class SendPhoneMessage {
    private String imageCode;

    private String phone;

    private Boolean isReg = true;
}

