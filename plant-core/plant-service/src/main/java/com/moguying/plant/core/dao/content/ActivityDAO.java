package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.content.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * ActivityDAO继承基类
 */
@Repository
public interface ActivityDAO extends BaseMapper<Activity> {
    List<Activity> activityListForHome(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<Activity> newestActivity();
    List<Activity> activityList();
    List<Activity> selectSelective(Activity activity);
    Integer updateByPrimaryKeyWithBLOBs(Activity activity);
}