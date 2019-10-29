package com.moguying.plant.core.service.teste.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.service.teste.TasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasteServiceImpl implements TasteService {


    @Autowired
    private SeedDAO seedDAO;

    @DS("write")
    @Override
    public ResultData<Boolean> buy(BuyOrder buyOrder) {

        return null;
    }
}
