package com.moguying.plant.core.service.farmer.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.FarmerEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.FarmerTrigger;
import com.moguying.plant.core.dao.farmer.*;
import com.moguying.plant.core.dao.fertilizer.FertilizerDAO;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.farmer.*;
import com.moguying.plant.core.entity.farmer.vo.EnergyItem;
import com.moguying.plant.core.entity.farmer.vo.FarmerData;
import com.moguying.plant.core.entity.farmer.vo.FarmerLevelGift;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.service.farmer.FarmerService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    private FarmerInfoDAO farmerInfoDAO;

    @Autowired
    private FarmerNoticeDAO farmerNoticeDAO;

    @Autowired
    private FarmerEnergyDAO farmerEnergyDAO;

    @Autowired
    private FarmerSignDAO farmerSignDAO;

    @Autowired
    private FarmerNoticeTplDAO farmerNoticeTplDAO;

    @Autowired
    private FarmerEnergyTriggerDAO farmerEnergyTriggerDAO;

    @Autowired
    private FarmerLogDAO farmerLogDAO;

    @Autowired
    private ReapDAO reapDAO;

    @Autowired
    private FarmerLevelDAO farmerLevelDAO;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private FertilizerDAO fertilizerDAO;

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;

    @Value("${farmer.energy.ok.ok}")
    private long energyOk;

    @Value("${farmer.energy.ok.min}")
    private long energyOkMin;

    @Value("${farmer.energy.ok.max}")
    private long energyOkMax;

    @Value("${farmer.energy.lose}")
    private long energyLose;

    @Override
    @DS("write")
    public FarmerData userFarmerData(Integer userId) {
        //用户成长值
        FarmerData farmerData = new FarmerData();
        FarmerInfo farmerInfo = farmerInfoDAO.selectById(userId);
        if(null == farmerInfo) {
            farmerInfoDAO.insert(new FarmerInfo(userId));
        } else {
            farmerData.setGrowUpCount(farmerInfo.getGrowUpCount());
            farmerData.setFarmerLevel(farmerInfo.getFarmerLevel());
        }
        //是否有通知
        FarmerNotice noticeWhere = new FarmerNotice();
        noticeWhere.setUserId(userId);
        noticeWhere.setState(FarmerEnum.NOTICE_NO_READ.getState());
        farmerData.setHasNotice(farmerNoticeDAO.countNewNotice(noticeWhere) > 0);

        //用户能量信息
        FarmerEnergy energyWhere = new FarmerEnergy();
        energyWhere.setUserId(userId);
        energyWhere.setState(FarmerEnum.ENERGY_NO_GET.getState());
        energyWhere.setLimit(5);
        List<FarmerEnergy> energies = farmerEnergyDAO.selectSelective(energyWhere);
        List<EnergyItem> energyItems = new ArrayList<>();
        if(null != energies && !energies.isEmpty()){
            for(FarmerEnergy energy : energies){
                EnergyItem energyItem = new EnergyItem();
                energyItem.setId(energy.getId());
                long leftSecond = (new Date().getTime()  - energy.getAddTime().getTime()) / 1000;
                energyItem.setIncrGrowUpCount(energy.getIncrGrowUpCount());
                energyItem.setPickState(energyStateByLeftSecond(leftSecond));
                if(energyItem.getPickState().equals(FarmerEnum.ENERGY_HAS_LOSE.getState())){
                    if(energyLose(energy).getMessageEnum().equals(MessageEnum.SUCCESS)) continue;
                }
                if(leftSecond >= energyOkMin && leftSecond <= energyOk){
                    energyItem.setLeftSecond(energyOk - leftSecond);
                }

                if(leftSecond >= energyOkMax && leftSecond <= energyLose){
                    energyItem.setDismissSecond(energyLose - leftSecond);
                }
                energyItems.add(energyItem);
            }
        }
        farmerData.setEnergyItems(energyItems);

        FarmerSign signData = farmerSignDAO.selectById(userId);
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int result = null == signData ? 1 : Integer.valueOf(new String(signData.getSignCount()),2) ^ dayOfWeek;
        farmerData.setHasSign(result == 0);
        return farmerData;
    }

    /**
     * 通过剩余时间判断能量状态
     * @param leftSecond
     * @return
     */
    private Integer energyStateByLeftSecond(long leftSecond){
        if(leftSecond >= 0 && leftSecond < energyOk) {
            return FarmerEnum.ENERGY_NO_GROW.getState();
        } else if(leftSecond >= energyOk && leftSecond <= energyLose ) {
            return FarmerEnum.ENERGY_HAS_GROW.getState();
        } else {
            return FarmerEnum.ENERGY_HAS_LOSE.getState();
        }
    }




    @FarmerTrigger(action = "sign")
    @Override
    @DS("write")
    public ResultData<User> userDailySignIn(Integer userId) {
        ResultData<User> resultData = new ResultData<>(MessageEnum.ERROR,null);
        FarmerInfo farmerInfo = farmerInfoDAO.selectById(userId);
        if(null == farmerInfo) {
            farmerInfo = new FarmerInfo(userId);
            farmerInfoDAO.insert(farmerInfo);
        }
        resultData.setData(new User(farmerInfo.getUserId()));
        FarmerSign signData = farmerSignDAO.selectById(userId);
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(null == signData) {
            if(farmerSignDAO.insert(new FarmerSign(userId, Integer.toBinaryString(dayOfWeek).getBytes())) > 0)
                resultData.setMessageEnum(MessageEnum.SUCCESS);
        } else {
            int result = Integer.valueOf(new String(signData.getSignCount()),2) ^ dayOfWeek;
            if(result == 0)
                return resultData.setMessageEnum(MessageEnum.USER_HAS_SIGN);
            if(farmerSignDAO.updateById(new FarmerSign(userId,Integer.toBinaryString(dayOfWeek).getBytes())) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }


    @Override
    @DS("write")
    public PageResult<FarmerNotice> farmerNoticeList(Integer page , Integer size, FarmerNotice where) {

        IPage<FarmerNotice> pageResult =
                farmerNoticeDAO.selectPage(new Page<>(page, size), new QueryWrapper<>(where).orderByDesc("add_time"));
        //查看完已读
        farmerNoticeDAO.updateStateByUserId(where.getUserId(),FarmerEnum.NOTICE_READ.getState());
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    /**
     * 收集能量
     * @param energy
     * @return
     */
    @Transactional
    @Override
    @DS("write")
    public ResultData<Integer> pickUpEnergy(FarmerEnergy energy) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        //更新能量信息
        FarmerEnergy farmerEnergy = farmerEnergyDAO.selectByIdAndUserId(energy);
        if(null == farmerEnergy)
            return resultData.setMessageEnum(MessageEnum.FARMER_ENERGY_NO_EXIST);
        if(farmerEnergy.getState().equals(FarmerEnum.ENERGY_HAS_GET.getState()))
            return resultData.setMessageEnum(MessageEnum.FARMER_ENERGY_HAS_PICK);
        if(farmerEnergy.getState().equals(FarmerEnum.ENERGY_OUT_TIME.getState()))
            return resultData.setMessageEnum(MessageEnum.FARMER_ENERGY_HAS_LOSE);
        long leftSecond = (new Date().getTime()  - farmerEnergy.getAddTime().getTime()) / 1000;
        if(!energyStateByLeftSecond(leftSecond).equals(FarmerEnum.ENERGY_HAS_GROW.getState()))
            return resultData.setMessageEnum(MessageEnum.FARMER_ENERGY_CAN_NOT_GET);
        FarmerEnergy update = new FarmerEnergy();
        update.setId(farmerEnergy.getId());
        update.setState(FarmerEnum.ENERGY_HAS_GET.getState());
        if(farmerEnergyDAO.updateById(update) <= 0)
            return resultData;
        if(!updateFarmerInfo(farmerEnergy).getMessageEnum().equals(MessageEnum.SUCCESS)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultData;
        }
        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }

    /**
     * 领取等级礼包
     * @param userId
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> pickUpLevelGift(Integer userId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        FarmerInfo farmerInfo = farmerInfoDAO.selectById(userId);
        if(null == farmerInfo)
            return resultData;
        if(farmerInfo.getLevelGift())
            return resultData.setMessageEnum(MessageEnum.FARMER_LEVEL_UP_GIFT_TRUE);
        ResultData<Integer> giftResult = fertilizerService.distributeFertilizer("farmerLevel"+farmerInfo.getFarmerLevel(),userId);
        if(giftResult.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            FarmerInfo update = new FarmerInfo();
            update.setUserId(userId);
            update.setLevelGift(true);
            if(farmerInfoDAO.updateById(update) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
        } else
            return resultData.setMessageEnum(MessageEnum.FARMER_LEVEL_UP_GIFT_FALSE);
        return resultData;
    }

    /**
     * 能量失效处理
     * @param energy
     * @return
     */
    @Override
    @DS("write")
    public ResultData<Integer> energyLose(FarmerEnergy energy) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        FarmerEnergy farmerEnergy = farmerEnergyDAO.selectById(energy.getId());
        if(null == farmerEnergy)
            return resultData.setMessageEnum(MessageEnum.FARMER_ENERGY_NO_EXIST);
        long leftTime = (new Date().getTime() - farmerEnergy.getAddTime().getTime()) / 1000;
        if(leftTime < energyLose)
            return resultData;
        FarmerEnergy update = new FarmerEnergy();
        update.setId(energy.getId());
        update.setState(FarmerEnum.ENERGY_OUT_TIME.getState());
        if(farmerEnergyDAO.updateById(update) > 0) {
            return addNoticeToUserByTriggerEvent("loseEnergy",energy);
        }
        return resultData;
    }

    /**
     * 发放能量
     * @param triggerEvent
     * @param userId
     * @return
     */
    @Transactional
    @Override
    @DS("write")
    public ResultData<Integer> addEnergyToUserByTriggerEvent(String triggerEvent, Integer userId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        FarmerEnergyTrigger energyTriggers = farmerEnergyTriggerDAO.selectByTriggerEvent(triggerEvent);
        if(null != energyTriggers) {
            FarmerEnergy energy = new FarmerEnergy();
            energy.setUserId(userId);
            energy.setIncrWay(triggerEvent);
            energy.setAddTime(new Date());
            if(null != energyTriggers.getIncrGrowUpCount()){
                if(triggerEvent.equals("plant")) {
                    int isFirst = reapDAO.countPlantedByDateAndUserId(userId, DateUtil.INSTANCE.todayBegin(),DateUtil.INSTANCE.todayEnd());

                    //每日首次种植
                    if(isFirst == 1){
                        FarmerEnergy firstPlant = new FarmerEnergy();
                        firstPlant.setUserId(userId);
                        firstPlant.setIncrWay("firstPlant");
                        firstPlant.setAddTime(new Date());
                        firstPlant.setIncrGrowUpCount(energyTriggers.getIncrGrowUpCount());
                        energy.setIncrGrowUpCount(energyTriggers.getIncrGrowUpCount());
                        if(farmerEnergyDAO.insert(energy) > 0) {
                            resultData.setMessageEnum(MessageEnum.SUCCESS);
                            addNoticeToUserByTriggerEvent(firstPlant.getIncrWay(),firstPlant);
                        }
                    }
                } else
                    energy.setIncrGrowUpCount(energyTriggers.getIncrGrowUpCount());
            }
            if(null != energyTriggers.getIncrGrowUpRate()){
                //种植按照种植金额的百分比来计算能量值
                if(triggerEvent.equals("plant")) {
                    Reap reap = reapDAO.selectNewestByUserId(userId);
                    if(reap.getAddTime().getTime() <= DateUtil.INSTANCE.todayEnd().getTime()){
                        BigDecimal rate = energyTriggers.getIncrGrowUpRate();
                        energy.setIncrGrowUpCount(rate.multiply(reap.getPreAmount()).intValue());
                    }
                }
            }
            if(energy.getIncrGrowUpCount() > 0) {
                if (farmerEnergyDAO.insert(energy) > 0) {
                    addNoticeToUserByTriggerEvent(triggerEvent, energy);
                    return resultData.setMessageEnum(MessageEnum.SUCCESS);
                }
            }
        }
        return resultData;
    }

    /**
     * 添加通知
     */
    @Transactional
    @Override
    @DS("write")
    public ResultData<Integer> addNoticeToUserByTriggerEvent(String triggerEvent, FarmerEnergy energy) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        FarmerNoticeTpl tpl = farmerNoticeTplDAO.selectByTriggerEvent(triggerEvent);
        if(null == tpl)
            return resultData;
        String messageTpl = tpl.getMessageTpl();
        if(messageTpl.contains("{growUpCount}"))
            messageTpl = messageTpl.replace("{growUpCount}",energy.getIncrGrowUpCount().toString());
        if(messageTpl.contains("{level}"))
            messageTpl = messageTpl.replace("{level}",energy.getFarmerLevel().toString());

        FarmerNotice notice = new FarmerNotice();
        notice.setUserId(energy.getUserId());
        notice.setAddTime(new Date());
        notice.setMessage(messageTpl);
        if(farmerNoticeDAO.insert(notice) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }


    /**
     * 更新用户田园信息
     * @param energy
     * @return
     */
    @Transactional
    @Override
    @DS("write")
    public ResultData<Integer> updateFarmerInfo(FarmerEnergy energy) {
        FarmerInfo farmerInfo = farmerInfoDAO.selectById(energy.getUserId());
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(farmerInfo == null) {
            if(farmerInfoDAO.insert(new FarmerInfo(energy.getUserId())) > 0)
                return updateFarmerInfo(energy);
            return resultData;
        }
        //添加日志
        FarmerLog log = new FarmerLog();
        log.setUserId(energy.getUserId());
        log.setIncrGrowUpCount(energy.getIncrGrowUpCount());
        log.setGrowUpCount(farmerInfo.getGrowUpCount() + energy.getIncrGrowUpCount());
        log.setIncrWay(energy.getIncrWay());
        log.setAddTime(new Date());
        if(farmerLogDAO.insert(log) <= 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultData;
        }
        //更新用户田园种植信息
        FarmerInfo update = new FarmerInfo();
        update.setUserId(energy.getUserId());
        update.setGrowUpCount(log.getGrowUpCount());
        //等级是否不同
        Integer level = farmerLevelDAO.selectLevelByGrowUpCount(log.getGrowUpCount());
        if(level > farmerInfo.getFarmerLevel()){
            update.setLevelGift(false);
            update.setFarmerLevel(level);
            //发送升级通知
            energy.setFarmerLevel(level);
            addNoticeToUserByTriggerEvent("levelUp",energy);
        }
        if(farmerInfoDAO.updateById(update) <= 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultData;
        }
        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }

    @Override
    @DS("read")
    public ResultData<FarmerLevelGift> userLevelGift(Integer userId, Integer level) {
        FarmerLevelGift levelGift = new FarmerLevelGift();
        ResultData<FarmerLevelGift> resultData = new ResultData<>(MessageEnum.ERROR,levelGift);
        String triggerEvent = "farmerLevel" + level;
        Fertilizer where = new Fertilizer();
        where.setTriggerGetEvent(triggerEvent);
        List<Fertilizer> gifts = fertilizerDAO.selectList(new QueryWrapper<>(where));
        int count = userFertilizerDAO.countByTriggerEvent(userId,triggerEvent);
        levelGift.setFertilizers(gifts);
        levelGift.setHasPickUp(count > 0);
        return resultData.setMessageEnum(MessageEnum.SUCCESS);
    }


    @DS("write")
    @Override
    public ResultData<Integer> delNotice(FarmerNotice notice) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(fertilizerDAO.deleteById(notice.getId()) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }
}
