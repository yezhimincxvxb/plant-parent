package com.moguying.plant.core.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * plant_farmer_sign
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerSign implements Serializable {
    private Integer userId;

    private byte[] signCount;
}