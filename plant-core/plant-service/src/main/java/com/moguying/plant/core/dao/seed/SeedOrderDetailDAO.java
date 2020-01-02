package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.index.SeedDetailInfo;
import com.moguying.plant.core.entity.index.TotalTable;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedOrderDetailDAO继承基类
 */
@Repository
public interface SeedOrderDetailDAO extends BaseDAO<SeedOrderDetail> {
    IPage<SeedOrderDetail> selectSelective(Page<SeedOrderDetail> page, @Param("wq") SeedOrderDetail where);

    List<SeedOrderDetail> selectSelective(@Param("wq") SeedOrderDetail where);

    IPage<SeedOrderDetail> selectListByUserIdAndState(Page<SeedOrderDetail> page, @Param("userId") Integer userId, @Param("state") Integer state);

    IPage<SeedOrderDetail> selectUserPayListByUserId(Page<SeedOrderDetail> page, @Param("userId") Integer userId);

    Integer sumOrderCountBySeedId(@Param("seedId") Integer seedId, @Param("state") Integer state);

    Integer findPlantBlockIdById(Integer id);

    SeedOrderDetail selectByIdAndUserIdWithSeedTypeInfo(@Param("id") Integer id, @Param("userId") Integer userId);

    SeedOrderDetail selectByOrderNumber(String orderNumber);

    Integer getBuySeedNum(@Param("state") Integer state);

    TotalTable getBuyCountAndPrice(@Param("state") Integer state);

    List<SeedDetailInfo> getSeedDetailInfo(@Param("ids") List<Integer> ids, @Param("state") Integer state);
}