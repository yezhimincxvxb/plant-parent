package com.moguying.plant.core.entity.coin.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ExcReap implements Serializable {

    private static final long serialVersionUID = 4392958773591886157L;

    /**
     * 邮寄地址
     */
    private Integer addressId;

    /**
     * 兑换id
     */
    private Integer reapId;


    /**
     * 兑换重量
     */
    private BigDecimal excWeigh;
}
