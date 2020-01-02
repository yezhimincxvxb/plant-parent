package com.moguying.plant.core.service.account.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.constant.MoneyStateEnum;
import com.moguying.plant.constant.OrderPrefixEnum;
import com.moguying.plant.core.dao.account.MoneyRechargeDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.MoneyRecharge;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.account.vo.RechargeReview;
import com.moguying.plant.core.entity.system.PhoneMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.service.account.MoneyRechargeService;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.utils.CommonUtil;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class MoneyRechargeServiceImpl implements MoneyRechargeService {

    @Autowired
    private MoneyRechargeDAO moneyRechargeDAO;

    @Autowired
    private UserMoneyService moneyService;


    @Autowired
    private PhoneMessageService phoneMessageService;

    @Autowired
    private UserDAO userDAO;

    @Value("${recharge.review.phone}")
    private String reviewPhone;

    private final String reviewTemple = "{param1}手机号为{param2}正在充值{param3}元，此次审核码为{param4} ";
    private final String inAccount = "亲爱的{param1}用户您好：您充值的{param2}元现已到账，您可点击账户查询详情，祝您在蘑菇营购物愉快！";


    @Override
    public PageResult<MoneyRecharge> moneyRechargeList(Integer page, Integer size, MoneyRecharge where) {
        IPage<MoneyRecharge> pageResult = moneyRechargeDAO.selectSelective(new Page<>(page,size),where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }


    @Override
    @DS("slave")
    public MoneyRecharge rechargeInfoById(Integer id) {
        return moneyRechargeDAO.selectById(id);
    }



    @Override
    @DS("write")
    public ResultData<Integer> reviewRecharge(Integer reviewUid,RechargeReview review) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.SUCCESS,0);
        MoneyRecharge recharge = moneyRechargeDAO.selectById(review.getId());
        recharge.setReviewUid(reviewUid);
        if(phoneMessageService.validateMessage(reviewPhone,review.getCode()))
            return resultData.setMessageEnum(MessageEnum.MESSAGE_CODE_ERROR);

        //审核不通过
        if(!review.getState().equals(MoneyStateEnum.RECHARGE_SUCCESS.getState())) {
            recharge.setState(MoneyStateEnum.RECHARGE_FAILED.getState());
            moneyRechargeDAO.updateById(recharge);
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }

        if(recharge == null || !recharge.getState().equals(MoneyStateEnum.RECHARGING.getState()))
            return resultData.setMessageEnum(MessageEnum.RECHARGE_IS_REVIEWED);

        if(recharge.getMoney().compareTo(new BigDecimal("0.00")) <= 0)
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_AVAILABLE);
        BigDecimal rechargeMoney = recharge.getMoney().subtract(recharge.getFee());
        //手续费后不能为0或负数
        if(rechargeMoney.compareTo(new BigDecimal("0.00")) <= 0)
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_AVAILABLE);

        MoneyRecharge update = new MoneyRecharge();
        update.setId(recharge.getId());
        UserMoneyOperator operator = new UserMoneyOperator();
        operator.setOperationId(recharge.getOrderNumber());
        UserMoney userMoney = new UserMoney(recharge.getUserId());
        update.setToAccountTime(new Date());
        update.setToAccountMoney(rechargeMoney);
        update.setState(MoneyStateEnum.RECHARGE_SUCCESS.getState());
        update.setReviewUid(reviewUid);
        operator.setOpType(MoneyOpEnum.RECHARGE_DONE);
        userMoney.setAvailableMoney(rechargeMoney);
        moneyRechargeDAO.updateById(update);
        operator.setUserMoney(userMoney);
        if( null != moneyService.updateAccount(operator)) {
            User user = userDAO.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, recharge.getUserId()));
            phoneMessageService.send(user.getPhone(),inAccount,null,user.getPhone(),rechargeMoney.toString());
        }
        return resultData.setData(recharge.getId());
    }

    @Override
    @DS("write")
    public ResultData<Integer> addMoneyRecharge(MoneyRecharge addRecharge) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);

        User user = userDAO.selectOne(new QueryWrapper<User>().lambda().eq(User::getPhone, addRecharge.getPhone()));
        if(null == user) return resultData.setMessageEnum(MessageEnum.USER_NOT_EXISTS);

        if(addRecharge.getMoney().compareTo(new BigDecimal("0.00")) <= 0){
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_AVAILABLE);
        }
        addRecharge.setUserId(user.getId());
        addRecharge.setRechargeTime(new Date());
        if(addRecharge.getOrderNumber() == null) {
            addRecharge.setOrderNumber(OrderPrefixEnum.RECHARGE_ORDER.getPreFix() + DateUtil.INSTANCE.orderNumberWithDate());
        }
        if(moneyRechargeDAO.insert(addRecharge) > 0){
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(addRecharge.getId());
        }
        return resultData;
    }

    @Override
    @DS("write")
    public ResultData<Integer> reviewCode(RechargeReview review) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);

        MoneyRecharge where = new MoneyRecharge();
        where.setId(review.getId());
        Optional<MoneyRecharge> moneyRecharge = moneyRechargeDAO.selectSelective(where).stream().findFirst();

        if(!moneyRecharge.isPresent())
            return resultData.setMessageEnum(MessageEnum.RECHARGE_NOT_EXISTS);
        String code = CommonUtil.INSTANCE.messageCode();
        ResultData<Boolean> send = phoneMessageService.send(reviewPhone, reviewTemple, code,moneyRecharge.get().getRealName(), moneyRecharge.get().getPhone(), moneyRecharge.get().getMoney().toString(), code);
        if(send.getData())
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }
}
