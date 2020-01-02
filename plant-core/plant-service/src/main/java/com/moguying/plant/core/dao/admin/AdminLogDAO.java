package com.moguying.plant.core.dao.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.admin.AdminLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * AdminRoleDAO继承基类
 */
@Repository
public interface AdminLogDAO extends BaseDAO<AdminLog> {

    IPage<AdminLog> adminLogs(Page page, @Param("where") AdminLog where);
}