package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.mall.MallProductType;

import java.util.List;

public interface MallProductTypeService {

    List<MallProductType> typeList(MallProductType where);


    ResultData<Integer> saveType(MallProductType type);

    ResultData<Integer> deleteType(Integer id);




}
