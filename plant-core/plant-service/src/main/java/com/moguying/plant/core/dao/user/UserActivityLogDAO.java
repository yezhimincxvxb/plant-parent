package com.moguying.plant.core.dao.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.user.UserActivityLog;
import com.moguying.plant.core.entity.user.vo.UserActivityLogVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityLogDAO extends BaseDAO<UserActivityLog> {

    List<UserActivityLog> findOnToday(@Param("log") UserActivityLog log);

    IPage<UserActivityLogVo> activityLog(Page<UserActivityLogVo> page, @Param("activity") UserActivityLogVo userActivityLogVo);
}
