package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.mall.vo.MallOrderDetailVo;
import com.moguying.plant.core.entity.mall.vo.OrderDetailSearch;
import com.moguying.plant.core.entity.mall.vo.OrderItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MallOrderDetailService {

    List<OrderItem> orderItemListByOrderIdAndUserId(Integer orderId, Integer userId);

    PageResult<MallOrderDetailVo> mallOrderDetailList(Integer page,Integer size,OrderDetailSearch search);

    void downloadExcel(Integer userId, PageSearch<OrderDetailSearch> search, HttpServletRequest request);

}
