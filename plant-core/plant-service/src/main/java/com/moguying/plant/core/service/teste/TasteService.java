package com.moguying.plant.core.service.teste;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.taste.vo.TasteReap;

public interface TasteService {
    ResultData<BuyOrderResponse> buy(BuyOrder buyOrder, Integer userId);
    ResultData<TasteReap> reap(Integer userId, Integer orderId);
}
