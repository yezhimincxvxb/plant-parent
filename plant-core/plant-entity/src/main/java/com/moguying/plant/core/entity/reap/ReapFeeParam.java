package com.moguying.plant.core.entity.reap;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_reap_fee_param
 * @author 
 */
public class ReapFeeParam implements Serializable {
    /**
     * 菌包生长天数
     */
    private Integer growDays;

    /**
     * 种植费用利率
     */
    private BigDecimal reapFeeRate;

    /**
     * 复种【1首种，0复种】
     */
    private Integer isFirst;

    private static final long serialVersionUID = 1L;

    public Integer getGrowDays() {
        return growDays;
    }

    public void setGrowDays(Integer growDays) {
        this.growDays = growDays;
    }

    public BigDecimal getReapFeeRate() {
        return reapFeeRate;
    }

    public void setReapFeeRate(BigDecimal reapFeeRate) {
        this.reapFeeRate = reapFeeRate;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }
}