package com.moguying.plant.core.service.teste;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;

public interface TasteService {
    ResultData<BuyOrderResponse> buy(BuyOrder buyOrder, Integer userId);
}
