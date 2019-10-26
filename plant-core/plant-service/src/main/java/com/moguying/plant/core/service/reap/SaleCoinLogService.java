package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.entity.coin.SaleCoinLog;

/**
 * 蘑菇币日志接口
 */
public interface SaleCoinLogService {

    /**
     * 添加蘑菇币日志信息
     */
    
    int insertSaleCoinLog(SaleCoinLog saleCoinLog);
}
