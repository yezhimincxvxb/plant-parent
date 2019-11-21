package com.moguying.plant.core.service.account;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyRecharge;
import com.moguying.plant.core.entity.account.vo.RechargeReview;

public interface MoneyRechargeService {

    MoneyRecharge rechargeInfoById(Integer id);

    PageResult<MoneyRecharge> moneyRechargeList(Integer page, Integer size, MoneyRecharge where);

    ResultData<Integer> reviewRecharge(RechargeReview review);

    ResultData<Integer> addMoneyRecharge(MoneyRecharge addRecharge);

    ResultData<Integer> reviewCode(RechargeReview review);
}
