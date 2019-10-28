package com.moguying.plant.core.dao.fertilizer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.fertilizer.FertilizerType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FertilizerTypeDAO继承基类
 */
@Repository
public interface FertilizerTypeDAO extends BaseDAO<FertilizerType> {
    List<FertilizerType> selectSelective(FertilizerType where);
}