package com.moguying.plant.core.dao.mall;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.dto.MallCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MallCompanyDAO extends BaseMapper<MallCompany> {

    /**
     * 根据公司编码获取公司详情
     */
    MallCompany findByCode(String companyCode);

    /**
     * 获取所以公司名称
     */
    List<MallCompany> getAllComName();

}
