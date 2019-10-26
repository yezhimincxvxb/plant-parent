package com.moguying.plant.core.service.account;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyRecharge;

public interface MoneyRechargeService {

    MoneyRecharge rechargeInfoById(Integer id);

    PageResult<MoneyRecharge> moneyRechargeList(Integer page, Integer size, MoneyRecharge where);

    ResultData<Integer> reviewRecharge(Integer id, Boolean state);

    ResultData<Integer> addMoneyRecharge(MoneyRecharge addRecharge);
}
