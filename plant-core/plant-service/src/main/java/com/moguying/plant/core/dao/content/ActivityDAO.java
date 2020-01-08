package com.moguying.plant.core.dao.content;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.content.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ActivityDAO继承基类
 */
@Repository
public interface ActivityDAO extends BaseDAO<Activity> {
    IPage<Activity> activityListForHome(Page<Activity> page, @Param("wq") Activity activity);

    List<Activity> newestActivity();

    List<Activity> activityList();

    List<Activity> selectSelective(Activity activity);
}