package com.moguying.plant.core.entity.fertilizer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FertilizerDot implements Serializable {

    private Boolean hasDot;

    private Integer fertilizerType;

}
