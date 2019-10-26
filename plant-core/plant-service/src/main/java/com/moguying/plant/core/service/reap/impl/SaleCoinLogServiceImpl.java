package com.moguying.plant.core.service.reap.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.reap.SaleCoinLogDao;
import com.moguying.plant.core.entity.coin.SaleCoinLog;
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
    @DS("write")
    public int insertSaleCoinLog(SaleCoinLog saleCoinLog) {
        return saleCoinLogDao.insertSaleCoinLog(saleCoinLog);
    }
}
