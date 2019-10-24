package com.moguying.plant.core.service.farmer;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.farmer.FarmerData;
import com.moguying.plant.core.entity.farmer.FarmerEnergy;
import com.moguying.plant.core.entity.farmer.FarmerLevelGift;
import com.moguying.plant.core.entity.farmer.FarmerNotice;

public interface FarmerService {

    /**
     * 田园种植首页数据
     * @return
     */
    @DataSource("write")
    FarmerData userFarmerData(Integer userId);

    /**
     * 用户每日签到
     * @return
     */
    @DataSource("write")
    ResultData<User> userDailySignIn(Integer userId);


    /**
     * 田园种植通知列表
     * @return
     */
    @DataSource("write")
    PageResult<FarmerNotice> farmerNoticeList(Integer page, Integer size, FarmerNotice where);


    /**
     * 采摘能量值
     * @return
     */
    @DataSource("write")
    ResultData<Integer> pickUpEnergy(FarmerEnergy energy);


    /**
     * 领取等级礼包
     * @return
     */
    @DataSource("write")
    ResultData<Integer> pickUpLevelGift(Integer userId);


    /**
     * 能量失效
     * @param energy
     * @return
     */
    @DataSource("write")
    ResultData<Integer> energyLose(FarmerEnergy energy);


    /**
     * 由触发事件给用户发放能量
     * @param triggerEvent
     * @param userId
     * @return
     */
    @DataSource("write")
    ResultData<Integer> addEnergyToUserByTriggerEvent(String triggerEvent, Integer userId);


    /**
     * 由触发事件给用户发送通知
     * @param triggerEvent
     * @param energy
     * @return
     */
    @DataSource("write")
    ResultData<Integer> addNoticeToUserByTriggerEvent(String triggerEvent, FarmerEnergy energy);

    /**
     * 更新用户田园信息
     * @param energy
     * @return
     */
    @DataSource("write")
    ResultData<Integer> updateFarmerInfo(FarmerEnergy energy);

    @DataSource("read")
    ResultData<FarmerLevelGift> userLevelGift(Integer userId, Integer level);

    @DataSource
    ResultData<Integer> delNotice(FarmerNotice notice);

}


