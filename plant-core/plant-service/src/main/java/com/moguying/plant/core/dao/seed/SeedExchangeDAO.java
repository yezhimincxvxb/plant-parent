package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.seed.SeedExchange;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedExchangeDAO继承基类
 */
@Repository
public interface SeedExchangeDAO extends BaseMapper<SeedExchange> {
    List<SeedExchange> seedExchangeList(SeedExchange where);
}