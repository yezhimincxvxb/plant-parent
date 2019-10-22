package com.moguying.plant.core.dao.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.TriggerEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TriggerEventDAO继承基类
 */
@Repository
public interface TriggerEventDAO extends BaseMapper<TriggerEvent> {
    List<TriggerEvent> selectSelective(TriggerEvent where);
}