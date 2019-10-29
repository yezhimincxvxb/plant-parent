package com.moguying.plant.core.dao.mall;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.mall.MallProductType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MallProductTypeDAO继承基类
 */
@Repository
public interface MallProductTypeDAO extends BaseDAO<MallProductType> {
    @Override
    List<MallProductType> selectSelective(MallProductType where);
}