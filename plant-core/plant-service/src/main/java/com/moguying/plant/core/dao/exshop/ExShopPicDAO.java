package com.moguying.plant.core.dao.exshop;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.exshop.ExShopPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ExShopPicDAO继承基类
 */
@Repository
public interface ExShopPicDAO extends BaseDAO<ExShopPic> {
    int insertBatch(@Param("pics") List<ExShopPic> pics);

    @Override
    List<ExShopPic> selectSelective(ExShopPic where);

    List<ExShopPic> getPicByIdList(@Param("idList") List<Integer> idList);
}