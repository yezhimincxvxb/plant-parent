package com.moguying.plant.core.entity.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.moguying.plant.core.entity.common.vo.Profit;
import com.moguying.plant.utils.BigDecimalSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalProfit implements Profit, Serializable {

    /**
     * 总利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 1)
    private BigDecimal totalProfit;


    /**
     * 种植中的利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 2)
    private BigDecimal plantingProfit;


    /**
     * 已完成利润
     */
    @JSONField(serializeUsing = BigDecimalSerialize.class,ordinal = 3)
    private BigDecimal plantedProfit;


    /**
     * 累计种植
     */
    @JSONField(ordinal = 4)
    private Integer totalPlanted = 0;


    /**
     * 种植中份数
     */
    @JSONField(ordinal = 5)
    private Integer plantedCount = 0;

    /**
     * 采摘份数
     */
    @JSONField(ordinal = 6)
    private Integer reapCount = 0;

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
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

    public Integer getTotalPlanted() {
        return totalPlanted;
    }

    public void setTotalPlanted(Integer totalPlanted) {
        this.totalPlanted = totalPlanted;
    }

    public Integer getPlantedCount() {
        return plantedCount;
    }

    public void setPlantedCount(Integer plantedCount) {
        this.plantedCount = plantedCount;
    }

    public Integer getReapCount() {
        return reapCount;
    }

    public void setReapCount(Integer reapCount) {
        this.reapCount = reapCount;
    }
}
