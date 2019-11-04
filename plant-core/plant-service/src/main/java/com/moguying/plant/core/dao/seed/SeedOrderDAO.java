package com.moguying.plant.core.dao.seed;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.seed.SeedOrder;
import com.moguying.plant.core.entity.seed.vo.CanPlantOrder;
import com.moguying.plant.core.entity.user.vo.UserSeedOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SeedOrderDAO继承基类
 */
@Repository
public interface SeedOrderDAO extends BaseDAO<SeedOrder> {
    IPage<SeedOrder> selectSelective(Page<SeedOrder> page, @Param("wq") SeedOrder where);

    List<SeedOrder> selectSelective(@Param("wq") SeedOrder where);

    Integer incrSeedOrder(SeedOrder seedOrder);

    SeedOrder selectByUserId(Integer userId);

    SeedOrder selectByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);

    CanPlantOrder sumSeedCountBySeedType(@Param("seedType") Integer seedType, @Param("userId") Integer userId);

    List<UserSeedOrder> userSeedOrderStatistics(Integer userId);

    Integer sumSeedCountByUserId(Integer userId);
}