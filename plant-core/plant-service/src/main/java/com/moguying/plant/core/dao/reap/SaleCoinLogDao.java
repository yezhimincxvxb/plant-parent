package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.coin.SaleCoinLog;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 蘑菇币日志持久层
 */
@Repository
public interface SaleCoinLogDao extends BaseDAO<SaleCoinLog> {

    /**
     * 添加蘑菇币日志信息
     */
    int insertSaleCoinLog(SaleCoinLog saleCoinLog);

    /**
     * 显示兑换券记录
     */
    IPage<ExchangeInfo> showFertilizerLog(Page<ExchangeInfo> page, @Param("userId") Integer userId);
}
