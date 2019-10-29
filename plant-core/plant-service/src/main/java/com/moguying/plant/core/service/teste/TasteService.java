package com.moguying.plant.core.service.teste;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;

public interface TasteService {
    ResultData<Boolean> buy(BuyOrder buyOrder);
}
