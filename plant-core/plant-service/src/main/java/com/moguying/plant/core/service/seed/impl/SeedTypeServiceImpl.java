package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.seed.SeedContentDAO;
import com.moguying.plant.core.dao.seed.SeedGroupDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.SeedGroup;
import com.moguying.plant.core.entity.seed.SeedType;
import com.moguying.plant.core.service.seed.SeedTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SeedTypeServiceImpl implements SeedTypeService {

    @Value("${meta.content.img}")
    private String appendStr;

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private SeedContentDAO seedContentDAO;

    @Autowired
    private SeedGroupDAO seedGroupDAO;

    @Override
    @DS("write")
    public ResultData<Integer> seedTypeSave(SeedType seedType) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
        if(null != seedType.getContent().getContractContent())
            seedType.getContent().setContractContent(appendStr.concat(seedType.getContent().getContractContent()));
        if(null != seedType.getContent().getSeedIntroduce())
            seedType.getContent().setSeedIntroduce(appendStr.concat(seedType.getContent().getSeedIntroduce()));
        if(null == seedType.getId()) {
            if (seedTypeDAO.insert(seedType) > 0) {
                if (null != seedType.getContent()) {
                    seedType.getContent().setSeedType(seedType.getId());
                    seedContentDAO.insert(seedType.getContent());
                }
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(seedType.getId());
            }
        } else {
            if(seedTypeDAO.updateById(seedType) > 0){
                if(null != seedType.getContent()){
                    seedType.getContent().setSeedType(seedType.getId());
                    seedContentDAO.updateById(seedType.getContent());
                }
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
            }
        }
        return resultData;
    }


    @Override
    @DS("read")
    public PageResult<SeedType> seedTypeList(Integer page, Integer size, SeedType seedClass) {
        IPage<SeedType> pageResult = seedTypeDAO.selectSelective(new Page<>(page, size), seedClass);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("write")
    public Integer seedTypeDelete(Integer id) {
        //删除对应
        if(null != seedContentDAO.selectById(id)){
            seedContentDAO.deleteById(id);
        }

        return seedTypeDAO.deleteById(id);
    }

    @Override
    @DS("read")
    public SeedType seedType(Integer id) {
        return seedTypeDAO.selectByPrimaryKeyWithBLOB(id);
    }

    @Override
    @DS("read")
    public List<SeedGroup> seedGroupList() {
        return seedGroupDAO.selectList(new QueryWrapper<>());
    }

    @Override
    @DS("write")
    public ResultData<Boolean> saveSeedGroup(SeedGroup seedGroup) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        boolean success = false;
        if(null != seedGroup.getId())
            success = seedGroupDAO.updateById(seedGroup) > 0;
        else
            success = seedGroupDAO.insert(seedGroup) > 0;

        if(success)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        return resultData;

    }
}
