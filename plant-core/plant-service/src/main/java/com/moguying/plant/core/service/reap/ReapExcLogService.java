package com.moguying.plant.core.service.reap;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.reap.ReapExcLog;

public interface ReapExcLogService {
    PageResult<ReapExcLog> reapExcLogPageResult(Integer page, Integer size, ReapExcLog where);
}
