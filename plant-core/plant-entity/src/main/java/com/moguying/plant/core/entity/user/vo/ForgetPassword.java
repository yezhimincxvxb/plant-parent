package com.moguying.plant.core.entity.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ForgetPassword implements Serializable {

    private String phone;

    private String code;

    private String password;
}
