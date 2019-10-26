package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SeedOrderDetailService {


    
    PageResult<SeedOrderDetail> seedOrderDetailList(Integer page, Integer size, SeedOrderDetail where);

    
    PageResult<SeedOrderDetail> userSeedOrderList(Integer page, Integer size, Integer userId, Integer state);

    
    PageResult<SeedOrderDetail> selectUserPayListByUserId(Integer page, Integer size, Integer userId);

    
    Integer seedCanPlantBlockId(Integer id);

    
    SeedOrderDetail orderDetailByIdAndUserId(Integer id, Integer userId);

    
    SeedOrderDetail selectByOrderNumber(String orderNumber);

    
    ResultData<Integer> seedOrderCancel(Integer id, Integer userId);

    
    List<SeedOrderDetail> needPayOrderList();

    
    void downloadExcel(Integer userId, PageSearch<SeedOrderDetail> search, HttpServletRequest request);
}
