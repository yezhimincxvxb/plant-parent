package com.moguying.plant.core.dao.exshop;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.exshop.ExShop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ExShopDAO继承基类
 */
@Repository
public interface ExShopDAO extends BaseDAO<ExShop> {

    /**
     * 用于后台管理
     * @param where
     * @return
     */
    @Override
    List<ExShop> selectSelective(ExShop where);


}