package com.moguying.plant.core.controller.back;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.LotteryLog;
import com.moguying.plant.core.entity.activity.LotteryRule;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;
import com.moguying.plant.core.service.content.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Qinhir
 */
@RestController
@RequestMapping("/activity")
@Api(tags = "活动管理")
public class BActivityController {

    @Autowired
    private ActivityService activityService;


    @PostMapping("/list")
    @ApiOperation("活动列表")
    public PageResult<Activity> activityList(@RequestBody PageSearch<Activity> search) {
        return activityService.activityList(search.getPage(), search.getSize(), search.getWhere());
    }


    @GetMapping("/{id}")
    @ApiOperation("活动详情")
    public ResponseData<Activity> activityDetail(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.activityDetail(id));
    }


    @PostMapping
    @ApiOperation("添加活动")
    public ResponseData<Integer> addActivity(@RequestBody Activity addActivity) {
        Integer id = activityService.addActivity(addActivity);
        if (id > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), id);
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    @PutMapping("/{id}")
    @ApiOperation("编辑活动")
    public ResponseData<Integer> updateActivity(@RequestBody Activity updateActivity, @PathVariable Integer id) {
        updateActivity.setId(id);
        Integer rows = activityService.updateActivity(updateActivity);
        if (rows > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }

    
    @DeleteMapping("/{id}")
    @ApiOperation("删除活动")
    public ResponseData<Integer> deleteActivity(@PathVariable Integer id) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), activityService.deleteActivityById(id));
    }

    /**
     * 品宣部：后台查看，登录送菌包
     */
    @PostMapping("/prize/log")
    @ApiOperation("查看活动奖励")
    public PageResult<UserActivityLogVo> sendSeed(@RequestBody PageSearch<UserActivityLogVo> search) {
        return activityService.activityLog(search.getPage(), search.getSize(), search.getWhere());
    }


    @GetMapping("/lottery/rule/list")
    @ApiOperation("抽奖规则")
    public ResponseData<List<LotteryRule>> lotteryRuleList() {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),activityService.lotteryRuleList());
    }


    @PostMapping("/lottery/rule")
    @ApiOperation("添加抽奖规则")
    public ResponseData<Boolean> addLotteryRule(@RequestBody LotteryRule rule) {
        ResultData<Boolean> aBoolean = activityService.addLotteryRule(rule);
        return new ResponseData<>(aBoolean.getMessageEnum().getMessage(),aBoolean.getMessageEnum().getState());
    }

    @DeleteMapping("/lottery/rule/{id}")
    @ApiOperation("删除抽奖规则")
    public ResponseData<Boolean> deleteLotteryRule(@PathVariable String id){
        Boolean aBoolean = activityService.deleteLotteryRule(id);
        if(aBoolean)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }


    @PostMapping("/lottery/log")
    @ApiOperation("抽奖记录")
    public PageResult<LotteryLog> lotteryLogPageResult(@RequestBody PageSearch<LotteryLog> search){
        return activityService.lotteryLog(search.getPage(),search.getSize(),search.getWhere());
    }
}
