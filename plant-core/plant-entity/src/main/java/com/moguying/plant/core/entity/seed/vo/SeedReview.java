package com.moguying.plant.core.entity.seed.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeedReview  implements Serializable {

    private static final long serialVersionUID = -7012077225042385471L;

    private Boolean state;

    private String mark;

}
