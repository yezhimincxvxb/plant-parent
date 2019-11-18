package com.moguying.plant.core.service.reap.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.reap.ReapExcLogDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.reap.ReapExcLog;
import com.moguying.plant.core.service.reap.ReapExcLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReapExcLogServiceImpl implements ReapExcLogService {

    @Autowired
    private ReapExcLogDAO reapExcLogDAO;

    @Override
    public PageResult<ReapExcLog> reapExcLogPageResult(Integer page, Integer size, ReapExcLog where) {
        IPage<ReapExcLog> reapExcLogIPage = reapExcLogDAO.selectSelective(new Page(page, size), where);
        return new PageResult<>(reapExcLogIPage.getTotal(), reapExcLogIPage.getRecords());
    }
}
