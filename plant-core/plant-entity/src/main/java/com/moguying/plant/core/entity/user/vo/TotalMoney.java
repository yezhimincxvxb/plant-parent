package com.moguying.plant.core.entity.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金类
 */
@Data
public class TotalMoney implements Serializable {

    private static final long serialVersionUID = -8558867906462301358L;

    /**
     * 总预计收益
     */
    private BigDecimal totalPreProfit;

    /**
     * 总预计回收种子成本
     */
    private BigDecimal totalPreAmount;
}
