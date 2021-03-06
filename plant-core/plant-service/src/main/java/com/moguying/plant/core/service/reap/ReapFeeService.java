package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.ReapFee;
import com.moguying.plant.core.entity.reap.vo.FeeUpdateStateRequest;

import javax.servlet.http.HttpServletRequest;

public interface ReapFeeService {

    ResultData<Integer> addReapFee(ReapFee reapFee);


    ResultData<Boolean> updateState(FeeUpdateStateRequest stateRequest);


    PageResult<ReapFee> reapFeeList(Integer page, Integer size, ReapFee where);


    void downloadExcel(Integer userId, PageSearch<ReapFee> search, HttpServletRequest request);


}
