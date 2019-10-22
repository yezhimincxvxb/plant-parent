package com.moguying.plant.core.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * plant_farmer_level
 * @author 
 */
@Data
public class FarmerLevel implements Serializable {
    private Integer level;

    private Integer growUpCountMin;

    private Integer growUpCountMax;
}