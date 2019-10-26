package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    PageResult<Activity> activityList(Integer page, Integer size);

    Activity activityDetail(Integer id);

    
    Integer deleteActivityById(Integer id);

    
    Integer addActivity(Activity activity);

    
    Integer updateActivity(Activity update);

    
    PageResult<Activity> activityListForHome(Integer page, Integer size, Date startTime, Date endTime);

    
    List<Activity> newestActivity();






}
