package com.moguying.plant.core.service.admin;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.admin.AdminLog;

public interface AdminLogService {

    Integer save(AdminLog log);

    PageResult<AdminLog> adminLogs(PageSearch<AdminLog> search);
}
