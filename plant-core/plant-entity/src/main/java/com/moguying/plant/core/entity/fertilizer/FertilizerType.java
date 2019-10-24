package com.moguying.plant.core.entity.fertilizer;

import lombok.Data;

import java.io.Serializable;

/**
 * plant_fertilizer_type
 * @author 
 */
@Data
public class FertilizerType implements Serializable {
    private Integer id;

    /**
     * 肥料类型
     */
    private String typeName;

    private static final long serialVersionUID = 1L;

}