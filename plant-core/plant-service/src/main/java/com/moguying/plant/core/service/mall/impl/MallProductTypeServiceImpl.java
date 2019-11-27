package com.moguying.plant.core.service.mall.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.mall.MallProductTypeDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.MallProductType;
import com.moguying.plant.core.service.mall.MallProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MallProductTypeServiceImpl implements MallProductTypeService {

    @Autowired
    private MallProductTypeDAO mallProductTypeDAO;

    @Autowired
    private MallProductDAO mallProductDAO;

    @Override
    @DS("read")
    public List<MallProductType> indexTypeList() {
        return mallProductTypeDAO.selectList(new QueryWrapper<MallProductType>().lambda().orderByAsc(MallProductType::getTypeSort));
    }

    @Override
    @DS("read")
    public List<MallProductType> typeList(MallProductType where) {
        return mallProductTypeDAO.selectSelective(where);
    }

    @Override
    @DS("write")
    public ResultData<Integer> saveType(MallProductType type) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        if (null == type.getId()) {
            if (mallProductTypeDAO.insert(type) > 0)
                return resultData.setData(type.getId()).setMessageEnum(MessageEnum.SUCCESS);
            return resultData;
        } else {
            if (mallProductTypeDAO.updateById(type) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(type.getId());
            return resultData;
        }
    }

    @Override
    public ResultData<Integer> deleteType(Integer id) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        MallProduct where = new MallProduct();
        where.setTypeId(id);
        List<MallProduct> products = mallProductDAO.selectSelective(where);
        if (null != products && products.size() > 0)
            return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_TYPE_CAN_NOT_DELETE);
        if (mallProductTypeDAO.deleteById(id) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }
}
