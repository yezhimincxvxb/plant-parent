package com.moguying.plant.core.dao.user;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.user.UserActivityLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityLogDAO extends BaseDAO<UserActivityLog> {

    List<UserActivityLog> findOnToday(@Param("log") UserActivityLog log);
}
