package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.mall.vo.OrderItem;

import java.util.List;

public interface MallOrderDetailService {
    @DataSource("read")
    List<OrderItem> orderItemListByOrderIdAndUserId(Integer orderId, Integer userId);
}
