package com.moguying.plant.core.service.account.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.constant.MoneyStateEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.account.MoneyRechargeDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyRecharge;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.service.account.MoneyRechargeService;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@Transactional
public class MoneyRechargeServiceImpl implements MoneyRechargeService {

    @Autowired
    private MoneyRechargeDAO moneyRechargeDAO;

    @Autowired
    private UserMoneyService moneyService;


    @Override
    public PageResult<MoneyRecharge> moneyRechargeList(Integer page, Integer size, MoneyRecharge where) {
        IPage<MoneyRecharge> pageResult = moneyRechargeDAO.selectPage(new Page<>(page, size), new QueryWrapper<>(where));
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }


    @Override
    @DS("slave")
    public MoneyRecharge rechargeInfoById(Integer id) {
        return moneyRechargeDAO.selectByPrimaryKey(id);
    }


    @Override
    @DS("write")
    public ResultData<Integer> reviewRecharge(Integer id, Boolean state) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.SUCCESS, 0);
        MoneyRecharge recharge = moneyRechargeDAO.selectByPrimaryKey(id);

        if (recharge == null || !recharge.getState().equals(MoneyStateEnum.RECHARGING.getState()))
            return resultData.setMessageEnum(MessageEnum.RECHARGE_IS_REVIEWED);

        if (recharge.getMoney().compareTo(new BigDecimal("0.00")) <= 0)
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_AVAILABLE);
        BigDecimal rechargeMoney = recharge.getMoney().subtract(recharge.getFee());
        //手续费后不能为0或负数
        if (rechargeMoney.compareTo(new BigDecimal("0.00")) <= 0)
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_AVAILABLE);

        MoneyRecharge update = new MoneyRecharge();
        update.setId(recharge.getId());
        UserMoneyOperator operator = new UserMoneyOperator();
        operator.setOperationId(recharge.getOrderNumber());
        UserMoney userMoney = new UserMoney(recharge.getUserId());
        userMoney.setFreezeMoney(rechargeMoney.negate());
        if (state) {
            update.setToAccountTime(new Date());
            update.setToAccountMoney(rechargeMoney);
            update.setState(MoneyStateEnum.RECHARGE_SUCCESS.getState());
            operator.setOpType(MoneyOpEnum.RECHARGE_DONE);
            userMoney.setAvailableMoney(rechargeMoney);
        } else {
            update.setState(MoneyStateEnum.RECHARGE_FAILED.getState());
            operator.setOpType(MoneyOpEnum.RECHARGE_FAILED);
        }
        moneyRechargeDAO.updateById(update);
        operator.setUserMoney(userMoney);
        moneyService.updateAccount(operator);

        return resultData.setData(recharge.getId());
    }

    @Override
    @DS("write")
    public ResultData<Integer> addMoneyRecharge(MoneyRecharge addRecharge) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);

        if (addRecharge.getMoney().compareTo(new BigDecimal("0.00")) <= 0) {
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_AVAILABLE);
        }
        addRecharge.setRechargeTime(new Date());
        if (addRecharge.getOrderNumber() == null) {
            addRecharge.setOrderNumber(OrderPrefixEnum.RECHARGE_ORDER.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        }
        if (moneyRechargeDAO.insert(addRecharge) > 0) {
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(addRecharge.getId());
        }
        return resultData;
    }
}
