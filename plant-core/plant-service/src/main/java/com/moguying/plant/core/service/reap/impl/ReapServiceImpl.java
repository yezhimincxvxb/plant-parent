package com.moguying.plant.core.service.reap.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.constant.MoneyOpEnum;
import com.moguying.plant.core.constant.ReapEnum;
import com.moguying.plant.core.constant.SystemEnum;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.dto.*;
import com.moguying.plant.core.entity.vo.ExchangeInfo;
import com.moguying.plant.core.entity.vo.TotalMoney;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.reap.SaleCoinService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import com.moguying.plant.utils.DateUtil;
import com.moguying.plant.utils.DownloadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReapServiceImpl<T> implements ReapService {

    @Autowired
    private ReapDAO reapDAO;

    @Autowired
    private UserMoneyService moneyService;

    @Autowired
    private PhoneMessageService phoneMessageService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SaleCoinService saleCoinService;

    @Autowired
    private UserMoneyService userMoneyService;

    @Value("${excel.download.dir}")
    private String downloadDir;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private ReapService reapService;

    @Autowired
    private UserFertilizerService userFertilizerService;

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<Reap> reapList(Integer page, Integer size, Reap where) {
        reapDAO.selectSelective(where);
        return null;
    }

    @Override
    @DataSource("write")
    public Integer updateReapState(List<Integer> idList, Reap where) {
        //发送短信
        if (reapDAO.updateStateByRange(idList, where) > 0) {
            List<InnerMessage> messages = reapDAO.selectPhoneByRange(idList);
            for (InnerMessage message : messages) {
                phoneMessageService.sendOtherMessage(message, SystemEnum.PHONE_MESSAGE_SEED_REAP_TYPE.getState());
            }
            return 1;
        }
        return -1;
    }

    @Override
    @Transactional
    @DataSource("write")
    public Integer reapDone(List<Integer> idList) {
        return null;
    }

    @Override
    @DataSource("read")
    public List<Reap> reapListByUserId(Integer userId) {
        return reapDAO.selectListCountByUserId(userId, ReapEnum.REAP_DONE.getState());
    }

    @Override
    @DataSource("read")
    public BigDecimal reapProfitStatistics(Integer userId, Date startTime, Date endTime, List<ReapEnum> reapEnums) {
        BigDecimal result = reapDAO.reapProfitStatistics(userId, startTime, endTime, reapEnums);
        return Optional.ofNullable(result).orElse(BigDecimal.ZERO);
    }

    @Override
    @DataSource("read")
    public BigDecimal plantProfitStatistics(Integer userId, Date startTime, Date endTime, ReapEnum reapEnum) {
        BigDecimal result;
        if (reapEnum == null) {
            result = reapDAO.plantProfitStatistics(userId, startTime, endTime, null);
        } else {
            result = reapDAO.plantProfitStatistics(userId, startTime, endTime, reapEnum.getState());
        }

        if (result == null)
            return new BigDecimal(0);
        return result;
    }

    /**
     * 种植份数统计
     */
    @Override
    @DataSource("read")
    public Integer reapStatistics(Integer userId, ReapEnum reapEnum, Boolean isEqual) {
        Integer result;
        if (reapEnum == null)
            result = reapDAO.reapStatistics(userId, null, isEqual);
        else
            result = reapDAO.reapStatistics(userId, reapEnum.getState(), isEqual);
        return null == result ? 0 : result;
    }

    /**
     * 出售成品
     */
    @TriggerEvent(action = "sale")
    @Transactional
    @Override
    @DataSource("write")
    public ResultData<TriggerEventResult<InnerMessage>> saleReap(Integer seedType, Integer userId) {
        ResultData<TriggerEventResult<InnerMessage>> resultData = new ResultData<>(MessageEnum.ERROR, null);
        User userInfo = userDAO.userInfoById(userId);
        Reap where = new Reap();
        where.setUserId(userId);
        where.setState(ReapEnum.REAP_DONE.getState());
        where.setSeedType(seedType);
        List<Reap> reapList = reapDAO.selectSelective(where);
        if (null == reapList)
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_EXISTS);
        if (reapList.size() <= 0)
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_CAN_NOT_SALE);
        BigDecimal totalAmount = BigDecimal.ZERO;
        InnerMessage message = new InnerMessage();
        message.setUserId(userId);
        message.setPhone(userInfo.getPhone());
        for (Reap reap : reapList) {
            //更新成品状态
            Reap updateReap = new Reap();
            updateReap.setId(reap.getId());
            updateReap.setRecAmount(reap.getPreAmount());
            updateReap.setRecProfit(reap.getPreProfit());
            updateReap.setSaleTime(new Date());
            updateReap.setState(ReapEnum.SALE_DONE.getState());
            if (reapDAO.updateById(updateReap) <= 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData.setMessageEnum(MessageEnum.SEED_REAP_SALE_ERROR);
            }
            //更新用户本金
            UserMoneyOperator capitalOperator = new UserMoneyOperator();
            capitalOperator.setOpType(MoneyOpEnum.SALE_REAP_SEED);
            capitalOperator.setOperationId(reap.getOrderNumber());
            UserMoney capital = new UserMoney(userId);
            capital.setAvailableMoney(reap.getPreAmount());
            capital.setCollectCapital(reap.getPreAmount().negate());
            capitalOperator.setUserMoney(capital);
            if (moneyService.updateAccount(capitalOperator) == null)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            //更新用户收益
            UserMoney profit = new UserMoney(userId);
            UserMoneyOperator profitOperator = new UserMoneyOperator();
            profitOperator.setOperationId(reap.getOrderNumber());
            profitOperator.setOpType(MoneyOpEnum.SALE_REAP_SEED_PROFIT);
            profit.setAvailableMoney(reap.getPreProfit());
            profit.setCollectInterest(reap.getPreProfit().negate());
            profitOperator.setUserMoney(profit);
            if (moneyService.updateAccount(profitOperator) == null)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            totalAmount = totalAmount.add(reap.getPreAmount()).add(reap.getPreProfit());
            message.setSeedTypeName(reap.getSeedTypeName());
        }
        message.setAmount(totalAmount.toString());

        return resultData.setMessageEnum(MessageEnum.SUCCESS).
                setData(new TriggerEventResult<InnerMessage>().setData(message).setUserId(userId));
    }

    @Override
    @DataSource("read")
    public Reap reapInfoByIdAndUserId(Integer id, Integer userId) {
        Reap where = new Reap();
        where.setId(id);
        where.setUserId(userId);
        List<Reap> reaps = reapDAO.selectSelective(where);
        if (null != reaps && reaps.size() == 1)
            return reaps.get(0);
        return null;
    }

    @Override
    @DataSource("write")
    public Integer autoReap(Date reapTime) {
        List<Integer> reapIds = reapDAO.selectCanReapByTime(DateUtil.INSTANCE.todayEnd(reapTime));
        Reap update = new Reap();
        update.setRecReapTime(new Date());
        update.setState(ReapEnum.REAP_DONE.getState());
        if (reapIds.size() > 0)
            return updateReapState(reapIds, update);
        return -1;
    }

    @Override
    @DataSource("read")
    public void downloadExcel(Integer userId, PageSearch<Reap> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("采摘列表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadUtil<>(reapDAO, search, Reap.class, downloadInfo)).start();
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<ExchangeInfo> showReap(Integer page, Integer size, Integer userId) {
        reapDAO.showReap(userId);
        return null;
    }

    @Override
    @DataSource("read")
    public List<Reap> findReapListByName(String productName, Integer userId) {
        return reapDAO.findReapListByName(productName, userId);
    }

    @Override
    @DataSource("read")
    public Integer sumCoin(Integer userId, List<Integer> idList) {
        return reapDAO.sumCoin(userId, idList);
    }

    @Override
    @DataSource("read")
    public TotalMoney findMoney(List<Integer> idList) {
        return reapDAO.findMoney(idList);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    @DataSource("write")
    public Boolean exchangeMogubi(Integer userId, UserMoney userMoney, List<Reap> reaps) {

        // 用户未开通蘑菇币功能
        SaleCoin saleCoin = saleCoinService.findById(userId);
        if (saleCoin == null) {
            saleCoin = new SaleCoin();
            saleCoin.setUserId(userId);
            saleCoin.setCoinCount(0);
            if (saleCoinService.insertSaleCoin(saleCoin) <= 0) return false;
        }

        // 计算蘑菇币
        List<Integer> idList = reaps.stream().map(Reap::getId).collect(Collectors.toList());
        Integer coin = reapService.sumCoin(userId, idList);

        // 更新蘑菇币并新增蘑菇币日志
        saleCoin = saleCoinService.findById(userId);
        saleCoin.setCoinCount(saleCoin.getCoinCount() + coin);
        UserSaleCoin userSaleCoin = new UserSaleCoin();
        userSaleCoin.setSaleCoin(saleCoin);
        userSaleCoin.setUserId(userId);
        userSaleCoin.setAffectCoin(coin);
        userSaleCoin.setAffectType(1);
        String idStr = StringUtils.join(idList, ",");
        userSaleCoin.setAffectDetailId(idStr);
        if (saleCoinService.updateSaleCoin(userSaleCoin) == null) return false;

        TotalMoney money = reapService.findMoney(idList);
        if (money == null) return false;

        // 更新用户资金并新增用户资金日志
        UserMoneyOperator userMoneyOperator;
        for (Reap reap : reaps) {
            userMoneyOperator = new UserMoneyOperator();
            userMoneyOperator.setOpType(MoneyOpEnum.MUSHROOM_COIN);
            userMoneyOperator.setOperationId(reap.getOrderNumber());
            userMoneyOperator.setMark("兑换蘑菇币");
            // TODO 资金
            UserMoneyOperator operator;
            for (int i = 1; i <= 2; i++) {
                userMoney = new UserMoney(userId);
                if (i == 1) {
                    userMoney.setCollectCapital(reap.getPreAmount().negate());
                    userMoneyOperator.setUserMoney(userMoney);
                    operator = userMoneyService.updateAccount(userMoneyOperator);
                } else {
                    userMoney.setCollectInterest(reap.getPreProfit().negate());
                    userMoneyOperator.setUserMoney(userMoney);
                    operator = userMoneyService.updateAccount(userMoneyOperator);
                }
                if (operator == null) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
        }

        // 更新状态：已兑换
        return reapDAO.updateState(idList) > 0;
    }


    @Override
    @Pagination
    @DataSource("read")
    public PageResult<ExchangeInfo> showReapLog(Integer page, Integer size, Integer userId) {
        reapDAO.showReapLog(userId);
        return null;
    }

    @Override
    @Transactional
    @DataSource("write")
    public Boolean exchangeFertilizer(Integer userId, Integer fertilizerId, SaleCoin saleCoin, Fertilizer fertilizer) {

        // 发券
        String triggerGetEvent = fertilizer.getTriggerGetEvent();
        TriggerEventResult triggerEventResult = new TriggerEventResult().setUserId(userId);
        ResultData<Integer> resultData = fertilizerService.distributeFertilizer(triggerGetEvent, triggerEventResult, fertilizerId);
        if (resultData.getMessageEnum().equals(MessageEnum.ERROR)) return false;

        UserFertilizer userFertilizer = userFertilizerService.getUserFertilizer(userId, fertilizerId, null);
        if (userFertilizer == null) return false;

        // 更新蘑菇币并新增蘑菇币日志
        saleCoin.setCoinCount(saleCoin.getCoinCount() - fertilizer.getCoinFertilizer());
        UserSaleCoin userSaleCoin = new UserSaleCoin();
        userSaleCoin.setSaleCoin(saleCoin);
        userSaleCoin.setUserId(userId);
        userSaleCoin.setAffectCoin(fertilizer.getCoinFertilizer());
        userSaleCoin.setAffectType(3);
        userSaleCoin.setAffectDetailId(userFertilizer.getId().toString());
        return saleCoinService.updateSaleCoin(userSaleCoin) != null;
    }
}
