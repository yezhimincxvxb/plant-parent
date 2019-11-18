package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.mall.vo.OrderItem;

import java.util.List;

public interface MallOrderDetailService {

    List<OrderItem> orderItemListByOrderIdAndUserId(Integer orderId, Integer userId);
}
