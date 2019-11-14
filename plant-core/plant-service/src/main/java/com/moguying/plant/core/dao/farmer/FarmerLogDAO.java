package com.moguying.plant.core.dao.farmer;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.farmer.FarmerLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FarmerLogDAO继承基类
 */
@Repository
public interface FarmerLogDAO extends BaseDAO<FarmerLog> {

    List<FarmerLog> farmerLogList(@Param("userId") Integer userId, @Param("incrWay") String incrWay);

}