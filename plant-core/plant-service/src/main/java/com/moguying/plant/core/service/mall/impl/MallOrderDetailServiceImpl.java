package com.moguying.plant.core.service.mall.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.core.service.mall.MallOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallOrderDetailServiceImpl implements MallOrderDetailService {

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;

    @Override
    @DS("read")
    public List<OrderItem> orderItemListByOrderIdAndUserId(Integer orderId, Integer userId) {
        return mallOrderDetailDAO.selectDetailListByOrderId(orderId,userId);
    }
}
