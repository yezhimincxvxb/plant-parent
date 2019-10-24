package com.moguying.plant.core.entity.farmer;

import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import lombok.Data;

import java.util.List;

@Data
public class FarmerLevelGift {

    private boolean hasPickUp;

    private List<Fertilizer> fertilizers;

}
