package com.moguying.plant.core.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.fertilizer.TriggerEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TriggerEventDAO继承基类
 */
@Repository
public interface TriggerEventDAO extends BaseDAO<TriggerEvent> {
    List<TriggerEvent> selectSelective(TriggerEvent where);
}