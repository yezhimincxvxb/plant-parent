package com.moguying.plant.core.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class MonthProfit implements Profit, Serializable {

    /**
     * 本月利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal profit;

    /**
     * 本月种植中的利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantingProfit;

    /**
     * 本月已完成的种植利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal plantedProfit;


    /**
     * 下月种植中的利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class)
    private BigDecimal nextMonthPlantingProfit;

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getPlantingProfit() {
        return plantingProfit;
    }

    public void setPlantingProfit(BigDecimal plantingProfit) {
        this.plantingProfit = plantingProfit;
    }

    public BigDecimal getPlantedProfit() {
        return plantedProfit;
    }

    public void setPlantedProfit(BigDecimal plantedProfit) {
        this.plantedProfit = plantedProfit;
    }

    public BigDecimal getNextMonthPlantingProfit() {
        return nextMonthPlantingProfit;
    }

    public void setNextMonthPlantingProfit(BigDecimal nextMonthPlantingProfit) {
        this.nextMonthPlantingProfit = nextMonthPlantingProfit;
    }
}
