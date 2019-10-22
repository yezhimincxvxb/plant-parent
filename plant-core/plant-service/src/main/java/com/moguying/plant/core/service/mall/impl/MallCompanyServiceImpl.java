package com.moguying.plant.core.service.mall.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.dao.mall.MallCompanyDAO;
import com.moguying.plant.core.entity.dto.MallCompany;
import com.moguying.plant.core.service.mall.MallCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallCompanyServiceImpl implements MallCompanyService {

    @Autowired
    private MallCompanyDAO mallCompanyDAO;

    @Override
    @DataSource("read")
    public MallCompany findByCode(String companyCode) {
        return mallCompanyDAO.findByCode(companyCode);
    }

    @Override
    @DataSource("read")
    public List<MallCompany> getAllComName() {
        return mallCompanyDAO.getAllComName();
    }

}
