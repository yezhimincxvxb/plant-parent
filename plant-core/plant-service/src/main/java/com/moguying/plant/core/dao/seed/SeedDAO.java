package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.common.vo.HomeSeed;
import com.moguying.plant.core.entity.seed.vo.SeedDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedDAO继承基类
 */
@Repository
public interface SeedDAO extends BaseMapper<Seed> {
    IPage<Seed> selectSelective(Page<Seed> page, @Param("wq") Seed seed);
    Seed selectByPrimaryKeyWithBLOB(Integer id);
    Integer incrByPrimaryKey(Seed incrSeed);
    IPage<HomeSeed> selectSeedListForHome(Page<Seed> page);
    SeedDetail seedDetail(Integer id);
    Integer decrSeedLeftCount(@Param("count") Integer count, @Param("id") Integer id);
    List<HomeSeed> recommendSeed();
    HomeSeed selectOneFullSeed();
}