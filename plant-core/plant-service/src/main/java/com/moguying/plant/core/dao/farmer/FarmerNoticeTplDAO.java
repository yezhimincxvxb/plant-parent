package com.moguying.plant.core.dao.farmer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.farmer.FarmerNoticeTpl;
import org.springframework.stereotype.Repository;

/**
 * FarmerNoticeTplDAO继承基类
 */
@Repository
public interface FarmerNoticeTplDAO extends BaseMapper<FarmerNoticeTpl> {
    FarmerNoticeTpl selectByTriggerEvent(String triggerEvent);
}