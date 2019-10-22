package com.moguying.plant.core.service.seed.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.seed.SeedDaysDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.SeedDays;
import com.moguying.plant.core.service.seed.SeedDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SeedDaysServiceImpl implements SeedDaysService {

    @Autowired
    private SeedDaysDAO seedDaysDAO;

    @Pagination
    @Override
    @DataSource("read")
    public PageResult<SeedDays> seedDaysList(Integer page, Integer size) {
        seedDaysDAO.selectSelective(null);
        return null;
    }

    @Override
    @DataSource("write")
    public Integer seedDaysAdd(SeedDays seedDays) throws DuplicateKeyException {
        return seedDaysDAO.insert(seedDays);
    }

    @Override
    @DataSource("write")
    public Integer seedDaysDelete(Integer days) {
        return seedDaysDAO.deleteById(days);
    }

    @Override
    @DataSource("write")
    public Integer seedDaysEdit(SeedDays seedDays) {

        return seedDaysDAO.updateById(seedDays);
    }
}
