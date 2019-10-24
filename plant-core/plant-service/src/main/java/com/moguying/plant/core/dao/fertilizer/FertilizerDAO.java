package com.moguying.plant.core.dao.fertilizer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FertilizerDAO继承基类
 */
@Repository
public interface FertilizerDAO extends BaseMapper<Fertilizer> {
    List<Fertilizer> selectSelective(Fertilizer where);

    /**
     * 显示可兑换的券列表
     */
    List<ExchangeInfo> showFertilizer();

}