package com.moguying.plant.core.dao.farmer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.vo.FarmerLevel;
import org.springframework.stereotype.Repository;

/**
 * FarmerLevelDAO继承基类
 */
@Repository
public interface FarmerLevelDAO extends BaseMapper<FarmerLevel> {
    Integer selectLevelByGrowUpCount(Integer growUpCount);
}