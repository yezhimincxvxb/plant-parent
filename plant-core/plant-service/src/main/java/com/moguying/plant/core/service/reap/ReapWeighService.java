package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.ReapWeigh;

public interface ReapWeighService {
    ResultData<ReapWeigh> userReapWeighInfo(Integer userId);

}
