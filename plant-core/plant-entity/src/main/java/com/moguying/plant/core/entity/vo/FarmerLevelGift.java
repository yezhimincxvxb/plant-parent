package com.moguying.plant.core.entity.vo;

import com.moguying.plant.core.entity.dto.Fertilizer;
import lombok.Data;

import java.util.List;

@Data
public class FarmerLevelGift {

    private boolean hasPickUp;

    private List<Fertilizer> fertilizers;

}
