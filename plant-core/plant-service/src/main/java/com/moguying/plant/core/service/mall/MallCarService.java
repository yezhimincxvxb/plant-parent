package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.mall.MallCar;
import com.moguying.plant.core.entity.mall.vo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface MallCarService {

    
    PageResult<OrderItem> userCarItems(Integer page, Integer size, Integer userId);

    
    ResultData<Integer> addItemToCar(MallCar mallCar);

    
    ResultData<Boolean> removeItemFromCar(List<OrderItem> ids, Integer userId);

    
    ResultData<Integer> modifyItemCount(OrderItem orderItem);

    
    BigDecimal getCheckedItemAmount(Integer userId);

    
    ResultData<Integer> checkItems(List<OrderItem> items, Integer userId, Boolean check);

    
    Boolean isAllCheck(Integer userId);
}
