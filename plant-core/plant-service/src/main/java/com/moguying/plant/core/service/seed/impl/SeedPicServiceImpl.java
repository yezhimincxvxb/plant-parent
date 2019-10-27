package com.moguying.plant.core.service.seed.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.core.dao.seed.SeedPicDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.seed.SeedPic;
import com.moguying.plant.core.service.seed.SeedPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        IPage<SeedPic> pageResult = seedPicDAO.selectPage(new Page<>(page, size), new QueryWrapper<>(seedPic));
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
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
        return seedPicDAO.selectList(new QueryWrapper<>(seedPic));
    }

    @Override
    @DS("read")
    public List<SeedPic> seedPicByRange(List<Integer> range) {
        return seedPicDAO.selectBatchIds(range).stream().filter((x)->x.getIsDelete() != 0).collect(Collectors.toList());
    }

    @Override
    @DS("read")
    public SeedPic seedPicById(Long id) {
        return seedPicDAO.selectById(id);
    }
}
