package com.moguying.plant.core.service.content;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityService extends IService<Activity> {

    PageResult<Activity> activityList(Integer page, Integer size, Activity where);

    Activity activityDetail(Integer id);

    
    Integer deleteActivityById(Integer id);

    
    Integer addActivity(Activity activity);

    
    Integer updateActivity(Activity update);

    
    PageResult<Activity> activityListForHome(Integer page, Integer size, Date startTime, Date endTime);

    
    List<Activity> newestActivity();






}
