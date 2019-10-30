package com.moguying.plant.core.entity.seed.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuyOrder implements Serializable {


    private static final long serialVersionUID = -5177284057126377424L;
    /**
     * 购买份数
     */
    private Integer count;

    /**
     * 购买菌包id
     */
    private Integer seedId;


    /**
     * 是否不使用余额支付
     */
    private Boolean isPayByAccount;

}
