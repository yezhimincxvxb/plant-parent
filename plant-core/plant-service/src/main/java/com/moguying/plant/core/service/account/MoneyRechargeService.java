package com.moguying.plant.core.service.account;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.MoneyRecharge;

public interface MoneyRechargeService {

    @DataSource("read")
    MoneyRecharge rechargeInfoById(Integer id);

    @DataSource("read")
    PageResult<MoneyRecharge> moneyRechargeList(Integer page, Integer size, MoneyRecharge where);

    @DataSource("write")
    ResultData<Integer> reviewRecharge(Integer id, Boolean state);

    @DataSource("write")
    ResultData<Integer> addMoneyRecharge(MoneyRecharge addRecharge);
}
