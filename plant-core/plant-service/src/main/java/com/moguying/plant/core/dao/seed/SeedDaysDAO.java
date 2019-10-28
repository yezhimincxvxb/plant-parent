package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.seed.SeedDays;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedDaysDAO继承基类
 */
@Repository
public interface SeedDaysDAO extends BaseDAO<SeedDays> {

    List<SeedDays> selectSelective(SeedDays seedDays);
}