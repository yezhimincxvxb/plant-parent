package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.SeedDays;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedDaysDAO继承基类
 */
@Repository
public interface SeedDaysDAO extends BaseMapper<SeedDays> {

    List<SeedDays> selectSelective(SeedDays seedDays);
}