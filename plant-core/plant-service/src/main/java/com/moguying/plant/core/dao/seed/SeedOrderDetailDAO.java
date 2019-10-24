package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedOrderDetailDAO继承基类
 */
@Repository
public interface SeedOrderDetailDAO extends BaseMapper<SeedOrderDetail> {
    List<SeedOrderDetail> selectSelective(SeedOrderDetail where);
    List<SeedOrderDetail> selectListByUserIdAndState(@Param("userId") Integer userId, @Param("state") Integer state);
    List<SeedOrderDetail> selectUserPayListByUserId(Integer userId);
    Integer sumOrderCountBySeedId(@Param("seedId") Integer seedId, @Param("state") Integer state);
    Integer findPlantBlockIdById(Integer id);
    SeedOrderDetail selectByIdAndUserIdWithSeedTypeInfo(@Param("id") Integer id, @Param("userId") Integer userId);
    SeedOrderDetail selectByOrderNumber(String orderNumber);
}