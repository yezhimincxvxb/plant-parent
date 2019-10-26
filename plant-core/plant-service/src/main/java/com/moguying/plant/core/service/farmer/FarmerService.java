package com.moguying.plant.core.service.farmer;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.farmer.FarmerEnergy;
import com.moguying.plant.core.entity.farmer.FarmerNotice;
import com.moguying.plant.core.entity.farmer.vo.FarmerData;
import com.moguying.plant.core.entity.farmer.vo.FarmerLevelGift;
import com.moguying.plant.core.entity.user.User;

public interface FarmerService {

    /**
     * 田园种植首页数据
     * @return
     */
    
    FarmerData userFarmerData(Integer userId);

    /**
     * 用户每日签到
     * @return
     */
    
    ResultData<User> userDailySignIn(Integer userId);


    /**
     * 田园种植通知列表
     * @return
     */
    
    PageResult<FarmerNotice> farmerNoticeList(Integer page, Integer size, FarmerNotice where);


    /**
     * 采摘能量值
     * @return
     */
    
    ResultData<Integer> pickUpEnergy(FarmerEnergy energy);


    /**
     * 领取等级礼包
     * @return
     */
    
    ResultData<Integer> pickUpLevelGift(Integer userId);


    /**
     * 能量失效
     * @param energy
     * @return
     */
    
    ResultData<Integer> energyLose(FarmerEnergy energy);


    /**
     * 由触发事件给用户发放能量
     * @param triggerEvent
     * @param userId
     * @return
     */
    
    ResultData<Integer> addEnergyToUserByTriggerEvent(String triggerEvent, Integer userId);


    /**
     * 由触发事件给用户发送通知
     * @param triggerEvent
     * @param energy
     * @return
     */
    
    ResultData<Integer> addNoticeToUserByTriggerEvent(String triggerEvent, FarmerEnergy energy);

    /**
     * 更新用户田园信息
     * @param energy
     * @return
     */
    
    ResultData<Integer> updateFarmerInfo(FarmerEnergy energy);

    
    ResultData<FarmerLevelGift> userLevelGift(Integer userId, Integer level);

    
    ResultData<Integer> delNotice(FarmerNotice notice);

}


