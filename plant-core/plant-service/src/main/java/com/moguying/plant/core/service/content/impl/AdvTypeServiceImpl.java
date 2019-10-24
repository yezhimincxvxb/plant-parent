package com.moguying.plant.core.service.content.impl;

import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.content.AdvTypeDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.content.AdvType;
import com.moguying.plant.core.service.content.AdvTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvTypeServiceImpl implements AdvTypeService {

    Logger log  = LoggerFactory.getLogger(AdvTypeServiceImpl.class);



    @Autowired
    AdvTypeDAO advTypeDAO;

    @Override
    public Integer addAdvType(AdvType advType) {
        if(advTypeDAO.insert(advType) > 0)
            return advType.getId();
        return -1;
    }


    @Pagination
    @Override
    public PageResult<AdvType> advTypeList(Integer page, Integer size, AdvType advType) {
        advTypeDAO.selectSelective(advType);
        return null;
    }

    @Override
    public AdvType advType(Integer id) {
        return advTypeDAO.selectById(id);
    }

    @Override
    public Integer updateAdvType(Integer id ,AdvType update) {
        AdvType advType = advTypeDAO.selectById(id);

        if( advType != null) {
            update.setId(id);
            return advTypeDAO.updateById(update);
        }
        return -1;
    }
}


