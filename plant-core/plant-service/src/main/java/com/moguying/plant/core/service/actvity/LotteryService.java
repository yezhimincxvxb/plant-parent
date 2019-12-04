package com.moguying.plant.core.service.actvity;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.LotteryRule;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;

public interface LotteryService {
    LotteryQua userLotteryQua(Integer userId);

    ResultData<Boolean> addLotteryRule(LotteryRule rule);
}
