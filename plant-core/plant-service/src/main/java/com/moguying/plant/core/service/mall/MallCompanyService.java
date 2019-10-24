package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.mall.MallCompany;

import java.util.List;

public interface MallCompanyService {

    /**
     * 根据公司编码获取公司详情
     */
    @DataSource("read")
    MallCompany findByCode(String companyCode);

    /**
     * 获取所以公司名称
     */
    @DataSource("read")
    List<MallCompany> getAllComName();

}
