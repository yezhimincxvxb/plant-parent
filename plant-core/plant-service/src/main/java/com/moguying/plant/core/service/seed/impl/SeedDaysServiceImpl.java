package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.seed.SeedDaysDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.seed.SeedDays;
import com.moguying.plant.core.service.seed.SeedDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class SeedDaysServiceImpl implements SeedDaysService {

    @Autowired
    private SeedDaysDAO seedDaysDAO;

    @Override
    @DS("read")
    public PageResult<SeedDays> seedDaysList(Integer page, Integer size) {
        seedDaysDAO.selectSelective(null);
        return null;
    }

    @Override
    @DS("write")
    public Integer seedDaysAdd(SeedDays seedDays) throws DuplicateKeyException {
        return seedDaysDAO.insert(seedDays);
    }

    @Override
    @DS("write")
    public Integer seedDaysDelete(Integer days) {
        return seedDaysDAO.deleteById(days);
    }

    @Override
    @DS("write")
    public Integer seedDaysEdit(SeedDays seedDays) {

        return seedDaysDAO.updateById(seedDays);
    }
}
