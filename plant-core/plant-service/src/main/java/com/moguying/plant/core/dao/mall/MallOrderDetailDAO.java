package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.vo.MallOrderDetailVo;
import com.moguying.plant.core.entity.mall.vo.OrderDetailSearch;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MallOrderDetailDAO继承基类
 */
@Repository
public interface MallOrderDetailDAO extends BaseDAO<MallOrderDetail> {
    List<OrderItem> selectDetailListByOrderId(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    IPage<MallOrderDetailVo> selectSelective(Page<MallOrderDetailVo> page, @Param("wq") OrderDetailSearch user);
}