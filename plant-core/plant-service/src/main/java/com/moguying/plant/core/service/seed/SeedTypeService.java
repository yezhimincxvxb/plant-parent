package com.moguying.plant.core.service.seed;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.SeedGroup;
import com.moguying.plant.core.entity.seed.SeedType;

import java.util.List;

import java.math.BigDecimal;

public interface SeedTypeService {

    ResultData<Integer> seedTypeSave(SeedType seedClass);

    PageResult<SeedType> seedTypeList(Integer page, Integer size, SeedType seedClass);

    Integer seedTypeDelete(Integer id);

    SeedType seedType(Integer id);

    List<SeedGroup> seedGroupList();

    ResultData<Boolean> saveSeedGroup(SeedGroup seedGroup);

    SeedType getFreeSeed(String name, BigDecimal price, Boolean isDelete);

}
