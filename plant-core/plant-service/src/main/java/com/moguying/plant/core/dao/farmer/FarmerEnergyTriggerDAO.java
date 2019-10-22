package com.moguying.plant.core.dao.farmer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.vo.FarmerEnergyTrigger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FarmerEnergyTriggerDAO继承基类
 */
@Repository
public interface FarmerEnergyTriggerDAO extends BaseMapper<FarmerEnergyTrigger> {
    List<FarmerEnergyTrigger> selectSelective(FarmerEnergyTrigger where);
    FarmerEnergyTrigger selectByTriggerEvent(String triggerEvent);
}