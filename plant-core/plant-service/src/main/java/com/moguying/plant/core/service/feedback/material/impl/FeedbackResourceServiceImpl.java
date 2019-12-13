package com.moguying.plant.core.service.feedback.material.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.feedback.FeedbackMaterialDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.feedback.FeedbackMaterial;
import com.moguying.plant.core.service.feedback.material.DeleteMaterialService;
import com.moguying.plant.core.service.feedback.material.FeedbackMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FeedbackResourceServiceImpl implements FeedbackMaterialService {
    @Autowired
    private FeedbackMaterialDAO materialDAO;

    @Override
    @DS("read")
    public PageResult<FeedbackMaterial> findMaterialList(Integer page, Integer size, FeedbackMaterial where) {
        IPage<FeedbackMaterial> pageResult = materialDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("write")
    public ResultData<Integer> addMaterial(FeedbackMaterial material) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        if (materialDAO.insert(material) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(material.getId());
        return resultData;
    }


    @Override
    public ResultData<Boolean> deleteMaterial(FeedbackMaterial material) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR, false);
        if (materialDAO.updateById(material) > 0){
            new Thread(new DeleteMaterialService(material.getId())).start();
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }

    @Override
    public ResultData<Boolean> checkMaterial(String fileName) {
        if (materialDAO.selectCount(new QueryWrapper<FeedbackMaterial>().lambda().eq(FeedbackMaterial::getMaterialName, fileName).ne(FeedbackMaterial::getIsDelete, true)) > 0)
            return new ResultData<>(MessageEnum.FILE_EXISTS, false);
        return new ResultData<>(MessageEnum.SUCCESS, true);
    }

}
