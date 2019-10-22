package com.moguying.plant.core.service.reap.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.dao.reap.SaleCoinLogDao;
import com.moguying.plant.core.entity.dto.SaleCoinLog;
import com.moguying.plant.core.service.reap.SaleCoinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 蘑菇币日志接口实现类
 */
@Service
public class SaleCoinLogServiceImpl implements SaleCoinLogService {

    @Autowired
    private SaleCoinLogDao saleCoinLogDao;

    @Override
    @DataSource("write")
    public int insertSaleCoinLog(SaleCoinLog saleCoinLog) {
        return saleCoinLogDao.insertSaleCoinLog(saleCoinLog);
    }
}
