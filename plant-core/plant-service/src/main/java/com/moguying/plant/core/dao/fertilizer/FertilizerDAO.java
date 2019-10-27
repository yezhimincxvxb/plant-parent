package com.moguying.plant.core.dao.fertilizer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FertilizerDAO继承基类
 */
@Repository
public interface FertilizerDAO extends BaseMapper<Fertilizer> {
    IPage<Fertilizer> selectSelective(Page<Fertilizer> page, @Param("wq") Fertilizer where);

    /**
     * 显示可兑换的券列表
     */
    IPage<ExchangeInfo> showFertilizer(Page<ExchangeInfo> page);

}