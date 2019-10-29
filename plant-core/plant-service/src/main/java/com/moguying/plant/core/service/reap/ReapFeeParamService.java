package com.moguying.plant.core.service.reap;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.ReapFeeParam;

public interface ReapFeeParamService extends IService<ReapFeeParam> {

    ResultData<Boolean> saveParam(ReapFeeParam param);
}
