package com.moguying.plant.core.controller.api;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;
import com.moguying.plant.core.entity.activity.vo.LotteryResult;
import com.moguying.plant.core.service.content.ActivityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

@RestController
@RequestMapping("/activity")
public class AActivityController {

    @Autowired
    private ActivityService activityService;



    @ApiOperation("抽奖资格")
    @GetMapping("/lottery/qua")
    public ResponseData<LotteryQua> lotteryQua(@LoginUserId Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),activityService.lotteryQua(userId));
    }


    @ApiOperation("抽奖")
    @PostMapping("/lottery")
    public ResponseData<LotteryResult> lotteryDo(@LoginUserId Integer userId) {
        ResultData<LotteryResult> resultResultData = activityService.lotteryDo(userId);
        return new ResponseData<>(resultResultData.getMessageEnum().getMessage(),resultResultData.getMessageEnum().getState(),resultResultData.getData());
    }


}
