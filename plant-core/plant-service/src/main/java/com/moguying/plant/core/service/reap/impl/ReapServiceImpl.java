package com.moguying.plant.core.service.reap.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.constant.ReapEnum;
import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.reap.ReapWeighDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.UserSaleCoin;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.reap.ReapWeigh;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.entity.user.vo.TotalMoney;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.common.DownloadService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.reap.SaleCoinService;
import com.moguying.plant.core.service.system.PhoneMessageService;
import com.moguying.plant.core.service.user.UserFertilizerService;
import com.moguying.plant.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private ReapWeighDAO reapWeighDAO;

    @Override
    @DS("read")
    public PageResult<Reap> reapList(Integer page, Integer size, Reap where) {
        IPage<Reap> pageResult = reapDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("read")
    public PageResult<Reap> userReapList(Integer page, Integer size, Reap where) {
        IPage<Reap> pageResult = reapDAO.userReapList(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("write")
    public Integer updateReapState(List<Integer> idList, Reap where) {
        //发送短信
        if (reapDAO.updateStateByRange(idList, where) > 0) {
            List<Reap> reaps = reapDAO.selectReapInRange(idList, new Reap());
            reaps.forEach(reap -> {
                BigDecimal availableProfit = reap.getPreAmount().add(reap.getPreProfit());
                reapWeighDAO.incField(new ReapWeigh(reap.getUserId()).setAvailableProfit(availableProfit));
            });

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
    @DS("write")
    public Integer reapDone(List<Integer> idList) {
        return null;
    }

    @Override
    @DS("read")
    public List<Reap> reapListByUserId(Integer userId) {
        return reapDAO.selectListCountByUserId(userId, ReapEnum.REAP_DONE.getState());
    }

    @Override
    @DS("read")
    public BigDecimal reapProfitStatistics(Integer userId, Date startTime, Date endTime, List<ReapEnum> reapEnums) {
        BigDecimal result = reapDAO.reapProfitStatistics(userId, startTime, endTime, reapEnums);
        return Optional.ofNullable(result).orElse(BigDecimal.ZERO);
    }

    @Override
    @DS("read")
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
    @DS("read")
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
    @DS("write")
    public ResultData<TriggerEventResult<InnerMessage>> saleReap(Integer reapId, Integer userId) {
        ResultData<TriggerEventResult<InnerMessage>> resultData = new ResultData<>(MessageEnum.ERROR, null);
        User userInfo = userDAO.userInfoById(userId);
        Reap where = new Reap();
        where.setUserId(userId);
        where.setState(ReapEnum.REAP_DONE.getState());
        where.setId(reapId);
        Reap reap = reapDAO.selectOne(new QueryWrapper<>(where));
        if (null == reap)
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_EXISTS);

        BigDecimal totalAmount = BigDecimal.ZERO;
        InnerMessage message = new InnerMessage();
        message.setUserId(userId);
        message.setPhone(userInfo.getPhone());

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
        //更新已领取收益
        reapWeighDAO.incField(new ReapWeigh(userId).setHasProfit(totalAmount));
        reapWeighDAO.decField(new ReapWeigh(userId).setAvailableProfit(totalAmount));
        SeedType seedType = seedTypeDAO.selectById(reap.getSeedType());
        message.setSeedTypeName(seedType.getClassName());
        message.setAmount(totalAmount.toString());

        return resultData.setMessageEnum(MessageEnum.SUCCESS).
                setData(new TriggerEventResult<InnerMessage>().setData(message).setUserId(userId));
    }

    @Override
    @DS("read")
    public Reap reapInfoByIdAndUserId(Integer id, Integer userId) {
        Reap where = new Reap();
        where.setId(id);
        where.setUserId(userId);
        return reapDAO.selectOne(new QueryWrapper<>(where));
    }

    @Override
    @DS("write")
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
    @DS("read")
    public void downloadExcel(Integer userId, PageSearch<Reap> search, HttpServletRequest request) {
        DownloadInfo downloadInfo = new DownloadInfo("采摘列表", request.getServletContext(), userId, downloadDir);
        new Thread(new DownloadService<>(reapDAO, search, Reap.class, downloadInfo)).start();
    }

    @Override
    @DS("read")
    public PageResult<ExchangeInfo> showReap(Integer page, Integer size, Integer userId) {
        reapDAO.showReap(userId);
        return null;
    }

    @Override
    @DS("read")
    public List<Reap> findReapListByName(String productName, Integer userId) {
        return reapDAO.findReapListByName(productName, userId);
    }

    @Override
    @DS("read")
    public Integer sumCoin(Integer userId, List<Integer> idList) {
        return reapDAO.sumCoin(userId, idList);
    }

    @Override
    @DS("read")
    public TotalMoney findMoney(List<Integer> idList) {
        return reapDAO.findMoney(idList);
    }

    @Override
    @Transactional
    @DS("write")
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
    @DS("read")
    public PageResult<ExchangeInfo> showReapLog(Integer page, Integer size, Integer userId) {
        IPage<ExchangeInfo> pageResult = reapDAO.showReapLog(new Page<>(page, size), userId);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @Transactional
    @DS("write")
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


    @Override
    public BigDecimal getPlantProfits() {
        return reapDAO.getPlantProfits();
    }

    @Override
    public BigDecimal getPlantLines() {
        return reapDAO.getPlantLines();
    }


}
