package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.mall.MallOrderDetail;
import com.moguying.plant.core.entity.mall.vo.OrderItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MallOrderDetailService {
    
    List<OrderItem> orderItemListByOrderIdAndUserId(Integer orderId, Integer userId);

    PageResult<MallOrderDetail> mallOrderDetailList(Integer page, Integer size, MallOrderDetail search);

    void downloadExcel(Integer userId, PageSearch<MallOrderDetail> search, HttpServletRequest request);

}
