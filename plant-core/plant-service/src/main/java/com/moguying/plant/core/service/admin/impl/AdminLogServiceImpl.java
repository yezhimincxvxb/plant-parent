package com.moguying.plant.core.service.admin.impl;

import com.moguying.plant.core.dao.admin.AdminLogDAO;
import com.moguying.plant.core.entity.admin.AdminLog;
import com.moguying.plant.core.service.admin.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLogServiceImpl implements AdminLogService {
    @Autowired
    private AdminLogDAO adminLogDAO;

    @Override
    public Integer save(AdminLog log) {
        if(null != log.getId()){
            return adminLogDAO.updateById(log);
        } else {
            return adminLogDAO.insert(log);
        }
    }
}
