package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.SeedType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedTypeDAO继承基类
 */
@Repository
public interface SeedTypeDAO extends BaseMapper<SeedType> {

    List<SeedType> selectSelective(SeedType where);
    SeedType selectByPrimaryKeyWithBLOB(Integer id);

}