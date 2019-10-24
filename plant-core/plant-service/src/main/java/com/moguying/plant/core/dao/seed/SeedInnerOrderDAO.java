package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.seed.SeedInnerOrder;
import com.moguying.plant.core.entity.seed.SeedInnerOrderCount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedInnerOrderDAO继承基类
 */
@Repository
public interface SeedInnerOrderDAO extends BaseMapper<SeedInnerOrder> {

    List<SeedInnerOrderCount> innerOrderCountList();


}