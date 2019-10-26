package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.moguying.plant.core.dao.seed.SeedPicDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.seed.SeedPic;
import com.moguying.plant.core.service.seed.SeedPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeedPicServiceImpl implements SeedPicService {

    @Autowired
    private SeedPicDAO seedPicDAO;

    @Override
    @DS("read")
    public PageResult<SeedPic> seedPicList(Integer page, Integer size) {
        SeedPic seedPic = new SeedPic();
        //未删除
        seedPic.setIsDelete(new Byte("0"));
        seedPicDAO.selectSelective(seedPic);
        return null;
    }

    @Override
    @DS("write")
    public int seePicAdd(SeedPic seedPic) {
        return seedPicDAO.insert(seedPic);
    }

    @Override
    @DS("write")
    public SeedPic seedPicDelete(Long id) {
        SeedPic deletePic = seedPicDAO.selectById(id);
        if( deletePic == null)
            return null;

        if(seedPicDAO.deleteById(id) > 0 ) {
            //TODO 更新菌包关联的图片
            return deletePic;
        }

        return null;
    }

    @Override
    @DS("read")
    public List<SeedPic> seedPic(SeedPic seedPic) {
        return seedPicDAO.selectSelective(seedPic);
    }

    @Override
    @DS("read")
    public List<SeedPic> seedPicByRange(List<Integer> range) {
        return seedPicDAO.selectByRange(range);
    }

    @Override
    @DS("read")
    public SeedPic seedPicById(Long id) {
        return seedPicDAO.selectById(id);
    }
}