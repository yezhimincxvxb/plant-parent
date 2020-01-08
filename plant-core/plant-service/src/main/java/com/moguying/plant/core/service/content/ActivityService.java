package com.moguying.plant.core.service.content;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.activity.LotteryLog;
import com.moguying.plant.core.entity.activity.LotteryRule;
import com.moguying.plant.core.entity.activity.vo.LotteryQua;
import com.moguying.plant.core.entity.activity.vo.LotteryResult;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;

import java.util.Date;
import java.util.List;

public interface ActivityService extends IService<Activity> {

    PageResult<Activity> activityList(Integer page, Integer size, Activity where);

    Activity activityDetail(Integer id);

    
    Integer deleteActivityById(Integer id);

    
    Integer addActivity(Activity activity);

    
    Integer updateActivity(Activity update);

    
    PageResult<Activity> activityListForHome(Integer page, Integer size, Activity activity);

    
    List<Activity> newestActivity();

    PageResult<UserActivityLogVo> activityLog(Integer page, Integer size, UserActivityLogVo userActivityLogVo);

    LotteryQua lotteryQua(Integer userId);

    ResultData<LotteryResult> lotteryDo(Integer userId,boolean isCheck);

    ResultData<Boolean> addLotteryRule(LotteryRule rule);

    List<LotteryRule> lotteryRuleList();

    Boolean deleteLotteryRule(String id);

    PageResult<LotteryLog> lotteryLog(Integer page,Integer size,LotteryLog search);


}
