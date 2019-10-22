package com.moguying.plant.core.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * plant_farmer_info
 * @author 
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class FarmerInfo implements Serializable {
    /**
     * 用户id
     */
    @NonNull
    private Integer userId;

    /**
     * 用户等级
     */
    private Integer farmerLevel;

    /**
     * 生长值
     */
    private Integer growUpCount;

    /**
     * 是否有等级奖励
     */
    private Boolean levelGift;

}