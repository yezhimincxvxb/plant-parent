package com.moguying.plant.core.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Register implements Serializable {

    private String phone;

    private String password;

    private String msgCode;

    private String inviteCode;
}
