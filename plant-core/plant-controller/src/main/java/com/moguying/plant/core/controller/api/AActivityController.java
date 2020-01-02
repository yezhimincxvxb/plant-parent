package com.moguying.plant.core.controller.api;


import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.LotteryLog;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;
import com.moguying.plant.core.entity.activity.vo.LotteryQuaQuery;
import com.moguying.plant.core.entity.activity.vo.LotteryResult;
import com.moguying.plant.core.service.content.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/activity")
@Api(tags = "活动")
public class AActivityController {

    @Autowired
    private ActivityService activityService;


    @ApiOperation("抽奖资格")
    @GetMapping("/lottery/qua")
    public ResponseData<LotteryQua> lotteryQua(@LoginUserId Integer userId) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.lotteryQua(userId));
    }


    @ApiOperation("抽奖")
    @PostMapping("/lottery")
    public ResponseData<LotteryResult> lotteryDo(@LoginUserId Integer userId, @RequestBody LotteryQuaQuery quaQuery) {
        ResultData<LotteryResult> resultResultData = activityService.lotteryDo(userId,quaQuery.isCheck());
        return new ResponseData<>(resultResultData.getMessageEnum().getMessage(), resultResultData.getMessageEnum().getState(), resultResultData.getData());
    }

    @ApiOperation("个人抽奖记录")
    @PostMapping("/lottery/user/log")
    public PageResult<LotteryLog> lotteryUserLogPageResult(@LoginUserId Integer userId, @RequestBody PageSearch<LotteryLog> search) {
        LotteryLog where = Optional.ofNullable(search.getWhere()).orElseGet(LotteryLog::new);
        where.setUserId(userId);
        return activityService.lotteryLog(search.getPage(), search.getSize(), where);
    }

    @NoLogin
    @ApiOperation("抽奖记录")
    @PostMapping("/lottery/log")
    public PageResult<LotteryLog> lotteryLogPageResult(@RequestBody PageSearch<LotteryLog> search) {
        return activityService.lotteryLog(search.getPage(), search.getSize(), new LotteryLog());
    }

}
