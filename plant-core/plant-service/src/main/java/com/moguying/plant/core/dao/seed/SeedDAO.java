package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.Seed;
import com.moguying.plant.core.entity.vo.HomeSeed;
import com.moguying.plant.core.entity.vo.SeedDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedDAO继承基类
 */
@Repository
public interface SeedDAO extends BaseMapper<Seed> {
    List<Seed> selectSelective(Seed seed);
    Seed selectByPrimaryKeyWithBLOB(Integer id);
    Integer incrByPrimaryKey(Seed incrSeed);
    List<HomeSeed> selectSeedListForHome();
    SeedDetail seedDetail(Integer id);
    Integer decrSeedLeftCount(@Param("count") Integer count, @Param("id") Integer id);
    List<HomeSeed> recommendSeed();
    HomeSeed selectOneFullSeed();
}