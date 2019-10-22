package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.SaleCoinLog;
import com.moguying.plant.core.entity.vo.ExchangeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 蘑菇币日志持久层
 */
@Repository
public interface SaleCoinLogDao extends BaseMapper<SaleCoinLog> {

    /**
     * 添加蘑菇币日志信息
     */
    int insertSaleCoinLog(SaleCoinLog saleCoinLog);

    /**
     * 显示兑换券记录
     */
    List<ExchangeInfo> showFertilizerLog(Integer userId);
}
