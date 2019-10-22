package com.moguying.plant.core.service.content;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.Activity;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    @DataSource("read")
    PageResult<Activity> activityList(Integer page, Integer size);

    @DataSource("read")
    Activity activityDetail(Integer id);

    @DataSource("write")
    Integer deleteActivityById(Integer id);

    @DataSource("write")
    Integer addActivity(Activity activity);

    @DataSource("write")
    Integer updateActivity(Activity update);

    @DataSource("read")
    PageResult<Activity> activityListForHome(Integer page, Integer size, Date startTime, Date endTime);

    @DataSource("read")
    List<Activity> newestActivity();






}
