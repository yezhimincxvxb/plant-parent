package com.moguying.plant.core.service.account;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyRecharge;

public interface MoneyRechargeService {

    MoneyRecharge rechargeInfoById(Integer id);

    PageResult<MoneyRecharge> moneyRechargeList(Integer page, Integer size, MoneyRecharge where);

    ResultData<Integer> reviewRecharge(Integer id, Boolean state);

    ResultData<Integer> addMoneyRecharge(MoneyRecharge addRecharge);
}
