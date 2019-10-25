package com.moguying.plant.core.entity.farmer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("plant_farmer_energy_trigger")
public class FarmerEnergyTrigger implements Serializable {

    private static final long serialVersionUID = 5537076061935582831L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 事件触发名
     */
    @TableField
    @NonNull
    private String triggerEvent;

    /**
     * 事件触发的能量值
     */
    @TableField
    private Integer incrGrowUpCount;

    /**
     * 触发增长值与具体数值的百分比
     */
    @TableField
    private BigDecimal incrGrowUpRate;

}