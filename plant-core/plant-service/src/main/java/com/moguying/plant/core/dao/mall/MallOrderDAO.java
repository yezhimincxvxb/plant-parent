package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallOrderSearch;
import com.moguying.plant.core.entity.user.vo.UserMallOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MallOrderDAO继承基类
 */
@Repository
public interface MallOrderDAO extends BaseMapper<MallOrder> {
    List<MallOrder> selectSelective(MallOrderSearch where);
    List<UserMallOrder> userOrderListByState(@Param("userId") Integer userId, @Param("state") Integer state);

    MallOrder findByIdAndNum(@Param("userId") Integer userId, @Param("number") String number);
}