package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.AdvType;

public interface AdvTypeService {
    AdvType advType(Integer id);
    Integer addAdvType(AdvType advType);
    PageResult<AdvType> advTypeList(Integer page, Integer size, AdvType advType);
    Integer updateAdvType(Integer id, AdvType advType);
}
