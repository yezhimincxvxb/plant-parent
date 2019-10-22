package com.moguying.plant.core.service.seed;


import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.SeedType;

public interface SeedTypeService {

    @DataSource("write")
    ResultData<Integer> seedTypeSave(SeedType seedClass) ;

    @DataSource("read")
    PageResult<SeedType> seedTypeList(Integer page, Integer size, SeedType seedClass);

    @DataSource("write")
    Integer seedTypeDelete(Integer id);

    @DataSource("read")
    SeedType seedType(Integer id);

}
