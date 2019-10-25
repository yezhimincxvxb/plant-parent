package com.moguying.plant.core.entity.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendPhoneMessage implements Serializable {

    private static final long serialVersionUID = 5739426056101780711L;

    private String imageCode;

    private String phone;

    private Boolean isReg = true;
}

