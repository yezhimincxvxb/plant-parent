package com.moguying.plant.core.dao.farmer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.farmer.FarmerLog;
import org.springframework.stereotype.Repository;

/**
 * FarmerLogDAO继承基类
 */
@Repository
public interface FarmerLogDAO extends BaseMapper<FarmerLog> {
}