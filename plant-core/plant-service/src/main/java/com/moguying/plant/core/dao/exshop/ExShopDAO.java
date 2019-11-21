package com.moguying.plant.core.dao.exshop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.vo.ExShopVo;
import org.apache.ibatis.annotations.Param;
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

    IPage<ExShopVo> exShopList(Page<ExShopVo> page, @Param("shop") ExShop exShop);

}