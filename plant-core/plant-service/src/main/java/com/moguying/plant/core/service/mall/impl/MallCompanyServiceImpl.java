package com.moguying.plant.core.service.mall.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.mall.MallCompanyDAO;
import com.moguying.plant.core.entity.mall.MallCompany;
import com.moguying.plant.core.service.mall.MallCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallCompanyServiceImpl implements MallCompanyService {

    @Autowired
    private MallCompanyDAO mallCompanyDAO;

    @Override
    @DS("read")
    public MallCompany findByCode(String companyCode) {
        return mallCompanyDAO.findByCode(companyCode);
    }

    @Override
    @DS("read")
    public List<MallCompany> getAllComName() {
        return mallCompanyDAO.getAllComName();
    }

}
