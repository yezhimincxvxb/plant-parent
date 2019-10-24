package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.ReapFee;

import javax.servlet.http.HttpServletRequest;

public interface ReapFeeService {

    @DataSource("write")
    ResultData<Integer> addReapFee(ReapFee reapFee);


    @DataSource("read")
    PageResult<ReapFee> reapFeeList(Integer page, Integer size, ReapFee where);


    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<ReapFee> search, HttpServletRequest request);






}
