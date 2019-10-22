package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.SeedDays;
import org.springframework.dao.DuplicateKeyException;

public interface SeedDaysService {

    @DataSource("read")
    PageResult<SeedDays> seedDaysList(Integer page, Integer size);

    @DataSource("write")
    Integer seedDaysAdd(SeedDays seedDays) throws DuplicateKeyException;

    @DataSource("write")
    Integer seedDaysDelete(Integer days);

    @DataSource("write")
    Integer seedDaysEdit(SeedDays seedDays);


}
