package com.moguying.plant.core.service.content.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moguying.plant.core.dao.content.ActivityDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.Activity;
import com.moguying.plant.core.service.content.ActivityService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityDAO,Activity> implements ActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    @Value("${meta.content.img}")
    private String appendStr;

    @Override
    @DS("read")
    public PageResult<Activity> activityList(Integer page, Integer size, Activity where) {
        IPage<Activity> activityIPage = activityDAO.selectPage(new Page<>(page,size), new QueryWrapper<>(where));
        return new PageResult<>(activityIPage.getTotal(),activityIPage.getRecords());
    }

    @Override
    @DS("read")
    public Activity activityDetail(Integer id) {
        return activityDAO.selectById(id);
    }

    @Override
    @DS("write")
    public Integer deleteActivityById(Integer id) {
        return activityDAO.deleteById(id);
    }

    @Override
    @DS("write")
    public Integer addActivity(Activity activity) {
        if(null != activity.getContent())
            activity.setContent(appendStr.concat(activity.getContent()));
        if(activityDAO.insert(activity) > 0)
            return activity.getId();
        return 0;
    }

    @Override
    @DS("write")
    public Integer updateActivity(Activity update) {
        Activity activity = activityDAO.selectById(update.getId());
        if(null == activity)
            return -1;
        if(null != update.getContent())
            update.setContent(appendStr.concat(update.getContent()));
        return activityDAO.updateById(update);
    }

    @Override
    @DS("read")
    public PageResult<Activity> activityListForHome(Integer page,Integer size,Date startTime, Date endTime) {
        IPage<Activity> pageResult = activityDAO.activityListForHome(new Page<>(page,size),startTime,endTime);
        List<Activity> list = pageResult.getRecords();
        list.forEach((x)->{
            int compare = DateUtils.truncatedCompareTo(x.getOpenTime(),new Date(), Calendar.DATE);
            if( compare > 0)
                x.setState(0);
            else if(compare == 0)
                x.setState(1);
            else{
                int endTimeCompare = DateUtils.truncatedCompareTo(x.getCloseTime(),new Date(), Calendar.DATE);
                if(endTimeCompare > 0)
                    x.setState(1);
                else
                    x.setState(2);
            }
        });
        return new PageResult<>(pageResult.getTotal(),list);
    }

    @Override
    @DS("read")
    public List<Activity> newestActivity() {
        return activityDAO.newestActivity();
    }
}
