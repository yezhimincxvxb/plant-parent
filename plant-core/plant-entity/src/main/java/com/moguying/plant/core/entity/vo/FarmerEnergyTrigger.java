package com.moguying.plant.core.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * plant_farmer_energy_trigger
 * @author 
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class FarmerEnergyTrigger implements Serializable {
    private Integer id;

    /**
     * 事件触发名
     */
    @NonNull
    private String triggerEvent;

    /**
     * 事件触发的能量值
     */
    private Integer incrGrowUpCount;

    /**
     * 触发增长值与具体数值的百分比
     */
    private BigDecimal incrGrowUpRate;

}