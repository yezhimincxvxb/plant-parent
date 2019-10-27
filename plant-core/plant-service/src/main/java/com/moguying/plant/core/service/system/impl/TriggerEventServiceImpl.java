package com.moguying.plant.core.service.system.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.core.dao.system.TriggerEventDAO;
import com.moguying.plant.core.entity.fertilizer.TriggerEvent;
import com.moguying.plant.core.service.system.TriggerEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TriggerEventServiceImpl implements TriggerEventService {

    @Autowired
    private TriggerEventDAO triggerEventDAO;

    @Override
    @DS("read")
    public List<TriggerEvent> triggerEventList() {
        return triggerEventDAO.selectList(new QueryWrapper<>());
    }
}
