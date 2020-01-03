package com.moguying.plant.core.service.admin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.admin.AdminLogDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.admin.AdminLog;
import com.moguying.plant.core.service.admin.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminLogDAO adminLogDAO;

    @Override
    @DS("write")
    @Transactional
    public Integer save(AdminLog log) {
        if (null != log.getId()) {
            return adminLogDAO.updateById(log);
        } else {
            return adminLogDAO.insert(log);
        }
    }

    @Override
    @DS("read")
    public PageResult<AdminLog> adminLogs(PageSearch<AdminLog> search) {
        IPage<AdminLog> result = adminLogDAO.selectSelective(new Page<>(search.getPage(), search.getSize()), search.getWhere());
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
}
