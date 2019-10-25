package com.moguying.plant.core.entity.payment;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayWithCode implements Serializable {

    private static final long serialVersionUID = -5125011548467528308L;

    private Integer id;

    private String code;
}
