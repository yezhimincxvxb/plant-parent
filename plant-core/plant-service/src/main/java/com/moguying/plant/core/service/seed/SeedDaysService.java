package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.seed.SeedDays;
import org.springframework.dao.DuplicateKeyException;

public interface SeedDaysService {

    
    PageResult<SeedDays> seedDaysList(Integer page, Integer size);

    
    Integer seedDaysAdd(SeedDays seedDays) throws DuplicateKeyException;

    
    Integer seedDaysDelete(Integer days);

    
    Integer seedDaysEdit(SeedDays seedDays);


}
