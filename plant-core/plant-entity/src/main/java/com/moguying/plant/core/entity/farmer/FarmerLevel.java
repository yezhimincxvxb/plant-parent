package com.moguying.plant.core.entity.farmer;

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