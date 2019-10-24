package com.moguying.plant.core.service.seed.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.seed.SeedContentDAO;
import com.moguying.plant.core.dao.seed.SeedTypeDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.SeedType;
import com.moguying.plant.core.service.seed.SeedTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeedTypeServiceImpl implements SeedTypeService {

    Logger log = LoggerFactory.getLogger(SeedTypeServiceImpl.class);

    @Autowired
    private SeedTypeDAO seedTypeDAO;

    @Autowired
    private SeedContentDAO seedContentDAO;

    @Override
    @DataSource("write")
    public ResultData<Integer> seedTypeSave(SeedType seedType) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, 0);
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


    @Pagination
    @Override
    @DataSource("read")
    public PageResult<SeedType> seedTypeList(Integer page, Integer size, SeedType seedClass) {
        seedTypeDAO.selectSelective(seedClass);
        return null;
    }

    @Override
    @DataSource("write")
    public Integer seedTypeDelete(Integer id) {
        //删除对应
        if(null != seedContentDAO.selectById(id)){
            seedContentDAO.deleteById(id);
        }

        return seedTypeDAO.deleteById(id);
    }

    @Override
    @DataSource("read")
    public SeedType seedType(Integer id) {
        return seedTypeDAO.selectByPrimaryKeyWithBLOB(id);
    }

}
