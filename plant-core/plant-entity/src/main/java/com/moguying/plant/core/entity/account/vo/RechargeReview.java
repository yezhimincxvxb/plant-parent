package com.moguying.plant.core.entity.account.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RechargeReview implements Serializable {

    private static final long serialVersionUID = -1913394494115529820L;

    private Integer id;

    private String code;

    private Integer state;
}
