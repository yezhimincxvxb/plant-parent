package com.moguying.plant.core.controller.api;


import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class AActivityController {


    @ApiOperation("抽奖资格")
    @GetMapping("/lottery/qua")
    public ResponseData<LotteryQua> lotteryQua(@LoginUserId Integer userId) {

        return null;
    }


    @ApiOperation("抽奖")
    @PostMapping("/lottery")
    public ResponseData<Boolean> lotteryDo(@LoginUserId Integer userId) {
        return null;
    }














}
