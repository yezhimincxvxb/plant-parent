package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.vo.MallOrderSearch;
import com.moguying.plant.core.entity.user.vo.UserMallOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MallOrderDAO继承基类
 */
@Repository
public interface MallOrderDAO extends BaseDAO<MallOrder> {
    IPage<MallOrder> selectSelective(Page<MallOrder> page, @Param("wq") MallOrderSearch where);

    List<MallOrder> selectSelective(@Param("wq") MallOrderSearch where);

    IPage<UserMallOrder> userOrderListByState(Page<MallOrder> page, @Param("userId") Integer userId, @Param("state") Integer state);

    MallOrder findByIdAndNum(@Param("userId") Integer userId, @Param("number") String number);

    Integer closeOrder(@Param("ids") List<Integer> ids);
}