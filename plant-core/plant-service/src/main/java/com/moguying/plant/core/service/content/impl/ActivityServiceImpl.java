package com.moguying.plant.core.service.content.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.content.ActivityDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.service.content.ActivityService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<Activity> activityList(Integer page, Integer size) {
        activityDAO.activityList();
        return null;
    }

    @Override
    @DataSource("read")
    public Activity activityDetail(Integer id) {
        return activityDAO.selectById(id);
    }

    @Override
    @DataSource("write")
    public Integer deleteActivityById(Integer id) {
        return activityDAO.deleteById(id);
    }

    @Override
    @DataSource("write")
    public Integer addActivity(Activity activity) {
        if(activityDAO.insert(activity) > 0)
            return activity.getId();
        return 0;
    }

    @Override
    @DataSource("write")
    public Integer updateActivity(Activity update) {
        Activity activity = activityDAO.selectById(update.getId());
        if(null == activity)
            return -1;
        if(null == update.getContent())
            return activityDAO.updateById(update);
        return activityDAO.updateByPrimaryKeyWithBLOBs(update);
    }

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<Activity> activityListForHome(Integer page,Integer size,Date startTime, Date endTime) {
        List<Activity> activities = activityDAO.activityListForHome(startTime,endTime);
        for(Activity activity : activities){
            int compare = DateUtils.truncatedCompareTo(activity.getOpenTime(),new Date(), Calendar.DATE);
            if( compare > 0)
                activity.setState(0);
            else if(compare == 0)
                activity.setState(1);
            else{
                int endTimeCompare = DateUtils.truncatedCompareTo(activity.getCloseTime(),new Date(), Calendar.DATE);
                if(endTimeCompare > 0)
                    activity.setState(1);
                else
                    activity.setState(2);
            }

        }
        return null;
    }

    @Override
    @DataSource("read")
    public List<Activity> newestActivity() {
        return activityDAO.newestActivity();
    }
}
