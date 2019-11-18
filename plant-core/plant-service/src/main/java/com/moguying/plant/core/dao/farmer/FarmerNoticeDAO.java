package com.moguying.plant.core.dao.farmer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.farmer.FarmerNotice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FarmerNoticeDAO继承基类
 */
@Repository
public interface FarmerNoticeDAO extends BaseDAO<FarmerNotice> {
    Integer countNewNotice(FarmerNotice where);

    List<FarmerNotice> selectSelective(FarmerNotice where);

    Integer updateStateByUserId(@Param("userId") Integer userId, @Param("state") Integer state);
}