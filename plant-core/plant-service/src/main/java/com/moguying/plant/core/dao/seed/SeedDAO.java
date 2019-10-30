package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.common.vo.HomeSeed;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.vo.SeedDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedDAO继承基类
 */
@Repository
public interface SeedDAO extends BaseDAO<Seed> {
    IPage<Seed> selectSelective(Page<Seed> page, @Param("wq") Seed seed);
    List<Seed> selectSelective(@Param("wq") Seed seed);
    Seed selectByPrimaryKeyWithBLOB(Integer id);
    Integer incrByPrimaryKey(Seed incrSeed);
    IPage<HomeSeed> selectSeedListForHome(Page<Seed> page,@Param("wq") HomeSeed homeSeed);
    SeedDetail seedDetail(Integer id);
    Integer decrSeedLeftCount(@Param("count") Integer count, @Param("id") Integer id);
    List<HomeSeed> recommendSeed();
    HomeSeed selectOneFullSeed();

    /**
     * 返回包含分类信息的菌包信息
     * @return
     */
    Seed seedInfoWithTypeById(Integer id);
}