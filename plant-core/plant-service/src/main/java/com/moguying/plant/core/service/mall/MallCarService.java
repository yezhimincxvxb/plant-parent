package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.vo.MallCar;
import com.moguying.plant.core.entity.vo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface MallCarService {

    @DataSource("read")
    PageResult<OrderItem> userCarItems(Integer page, Integer size, Integer userId);

    @DataSource("write")
    ResultData<Integer> addItemToCar(MallCar mallCar);

    @DataSource("write")
    ResultData<Boolean> removeItemFromCar(List<OrderItem> ids, Integer userId);

    @DataSource("write")
    ResultData<Integer> modifyItemCount(OrderItem orderItem);

    @DataSource("read")
    BigDecimal getCheckedItemAmount(Integer userId);

    @DataSource("write")
    ResultData<Integer> checkItems(List<OrderItem> items, Integer userId, Boolean check);

    @DataSource("read")
    Boolean isAllCheck(Integer userId);
}
