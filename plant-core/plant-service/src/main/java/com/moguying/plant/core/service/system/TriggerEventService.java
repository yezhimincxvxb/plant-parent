package com.moguying.plant.core.service.system;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.dto.TriggerEvent;

import java.util.List;

public interface TriggerEventService {
    @DataSource("read")
    List<TriggerEvent> triggerEventList();
}
