package com.moguying.plant.core.dao.reap;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.SaleCoin;
import org.springframework.stereotype.Repository;

/**
 * 用户-蘑菇币持久层
 */
@Repository
public interface SaleCoinDao extends BaseMapper<SaleCoin> {

    /**
     * 根据ID查询
     */
    SaleCoin findById(Integer userId);

    /**
     * 添加用户-蘑菇币信息
     */
    int insertSaleCoin(SaleCoin saleCoin);

    /**
     * 更新蘑菇币c
     */
    int updateSaleCoin(SaleCoin saleCoin);

}
