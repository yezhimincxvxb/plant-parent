package com.moguying.plant.core.service.seed;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.SeedType;

public interface SeedTypeService {

    
    ResultData<Integer> seedTypeSave(SeedType seedClass) ;

    
    PageResult<SeedType> seedTypeList(Integer page, Integer size, SeedType seedClass);

    
    Integer seedTypeDelete(Integer id);

    
    SeedType seedType(Integer id);

}
