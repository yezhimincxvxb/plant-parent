package com.moguying.plant.core.entity.user.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class Login implements Serializable {
    private String phone;

    private String code;

    private String password;

    /**
     * 辅助：领取的奖励名称
     */
    private String name;
}
