package com.moguying.plant.core.controller.api;

import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.User;
import com.moguying.plant.core.entity.vo.*;
import com.moguying.plant.core.service.farmer.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/farmer")
public class AFarmerController {


    @Autowired
    private FarmerService farmerService;

    /**
     * 田园种植首页数据
     * @param userId
     * @return
     */
    @GetMapping
    @ResponseBody
    public ResponseData<FarmerData> farmerInfo(@LoginUserId Integer userId){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),
                farmerService.userFarmerData(userId));
    }


    /**
     * 用户签到
     * @param userId
     * @return
     */
    @PostMapping("/sign")
    @ResponseBody
    public ResponseData<Integer> userSignIn(@LoginUserId Integer userId){
        ResultData<User> resultData = farmerService.userDailySignIn(userId);
        return new ResponseData<>(((ResultData) resultData).getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 用户田园种植信息
     * @param search
     * @return
     */
    @PostMapping("/notices")
    @ResponseBody
    public PageResult<FarmerNotice> notices(@LoginUserId Integer userId, @RequestBody PageSearch<FarmerNotice> search){
        FarmerNotice where  = new FarmerNotice();
        if(null != search.getWhere())
            where = search.getWhere();
        where.setUserId(userId);
        return farmerService.farmerNoticeList(search.getPage(),search.getSize(),where);
    }

    /**
     * 删除信息
     * @param notice
     * @return
     */
    @PostMapping("/notices/del")
    @ResponseBody
    public ResponseData<Integer> delNotice(@RequestBody FarmerNotice notice){
        ResultData<Integer> resultData = farmerService.delNotice(notice);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }




    /**
     * 收集能量
     * @param userId
     * @param farmerEnergy
     * @return
     */
    @PostMapping("/pick/up/energy")
    @ResponseBody
    public ResponseData<Integer> pickUpEnergy(@LoginUserId Integer userId, @RequestBody FarmerEnergy farmerEnergy){
        farmerEnergy.setUserId(userId);
        ResultData<Integer> resultData = farmerService.pickUpEnergy(farmerEnergy);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 领取等级奖励
     * @param userId
     * @return
     */
    @PostMapping("/pick/up/gift")
    @ResponseBody
    public ResponseData<Integer> pickUpGift(@LoginUserId Integer userId){
        ResultData<Integer> resultData = farmerService.pickUpLevelGift(userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


    /**
     * 等级礼物列表
     * @param level
     * @return
     */
    @PostMapping("/level/up/gift")
    @ResponseBody
    public ResponseData<FarmerLevelGift> levelUpGift(@LoginUserId Integer userId, @RequestBody FarmerLevel level){
        ResultData<FarmerLevelGift> levelGift = farmerService.userLevelGift(userId,level.getLevel());
        return new ResponseData<>(levelGift.getMessageEnum().getMessage(),levelGift.getMessageEnum().getState(),levelGift.getData());
    }
}
