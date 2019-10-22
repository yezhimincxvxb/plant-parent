package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.SeedOrder;
import com.moguying.plant.core.entity.vo.CanPlantOrder;
import com.moguying.plant.core.entity.vo.UserSeedOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedOrderDAO继承基类
 */
@Repository
public interface SeedOrderDAO extends BaseMapper<SeedOrder> {
    List<SeedOrder> selectSelective(SeedOrder where);
    Integer incrSeedOrder(SeedOrder seedOrder);
    SeedOrder selectByUserId(Integer userId);
    SeedOrder selectByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    CanPlantOrder sumSeedCountBySeedType(@Param("seedType") Integer seedType, @Param("userId") Integer userId);
    List<UserSeedOrder> userSeedOrderStatistics(Integer userId);
    Integer sumSeedCountByUserId(Integer userId);
}