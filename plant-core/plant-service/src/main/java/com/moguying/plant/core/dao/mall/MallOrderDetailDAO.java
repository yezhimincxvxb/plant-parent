package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MallOrderDetailDAO继承基类
 */
@Repository
public interface MallOrderDetailDAO extends BaseMapper<MallOrderDetail> {
    List<OrderItem> selectDetailListByOrderId(@Param("orderId") Integer orderId, @Param("userId") Integer userId);
}