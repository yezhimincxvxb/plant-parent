package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.seed.SeedType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedTypeDAO继承基类
 */
@Repository
public interface SeedTypeDAO extends BaseDAO<SeedType> {
    IPage<SeedType> selectSelective(Page<SeedType> page, @Param("wq") SeedType where);
    List<SeedType> selectSelective(SeedType where);
    SeedType selectByPrimaryKeyWithBLOB(Integer id);

}