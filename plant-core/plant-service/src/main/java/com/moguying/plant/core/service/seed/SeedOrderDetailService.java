package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SeedOrderDetailService {


    @DataSource("read")
    PageResult<SeedOrderDetail> seedOrderDetailList(Integer page, Integer size, SeedOrderDetail where);

    @DataSource("read")
    PageResult<SeedOrderDetail> userSeedOrderList(Integer page, Integer size, Integer userId, Integer state);

    @DataSource("read")
    PageResult<SeedOrderDetail> selectUserPayListByUserId(Integer page, Integer size, Integer userId);

    @DataSource("read")
    Integer seedCanPlantBlockId(Integer id);

    @DataSource("read")
    SeedOrderDetail orderDetailByIdAndUserId(Integer id, Integer userId);

    @DataSource("read")
    SeedOrderDetail selectByOrderNumber(String orderNumber);

    @DataSource(value = "write")
    ResultData<Integer> seedOrderCancel(Integer id, Integer userId);

    @DataSource(value = "read")
    List<SeedOrderDetail> needPayOrderList();

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<SeedOrderDetail> search, HttpServletRequest request);
}
