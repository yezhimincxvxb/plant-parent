package com.moguying.plant.core.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.Express;
import org.springframework.stereotype.Repository;

/**
 * ExpressDAO继承基类
 */
@Repository
public interface ExpressDAO extends BaseMapper<Express> {
}