package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.mall.MallProductType;

import java.util.List;

public interface MallProductTypeService {

    List<MallProductType> typeList(MallProductType where);

    /**
     * APP首页类型接口，目前只支持手动在数据库中修改排序！后期希望更新到后台管理系统
     * @return
     */
    List<MallProductType> indexTypeList();

    ResultData<Integer> saveType(MallProductType type);

    ResultData<Integer> deleteType(Integer id);




}
