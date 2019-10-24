package com.moguying.plant.core.entity.farmer;

import com.moguying.plant.core.entity.farmer.vo.EnergyItem;
import lombok.Data;

import java.util.List;

@Data
public class FarmerData {

    /**
     * 成长值
     */
    private Integer growUpCount = 0;

    /**
     * 是否有新通知
     */
    private Boolean hasNotice = false;

    /**
     * 能量列表
     */
    private List<EnergyItem> energyItems;

    /**
     * 是否签到
     */
    private Boolean hasSign;

    /**
     * 等级
     */
    private Integer farmerLevel = 1;

}
