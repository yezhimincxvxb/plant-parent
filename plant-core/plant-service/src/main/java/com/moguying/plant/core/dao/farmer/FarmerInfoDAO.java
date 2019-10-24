package com.moguying.plant.core.dao.farmer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.farmer.FarmerInfo;
import org.springframework.stereotype.Repository;

/**
 * FarmerInfoDAO继承基类
 */
@Repository
public interface FarmerInfoDAO extends BaseMapper<FarmerInfo> {
}