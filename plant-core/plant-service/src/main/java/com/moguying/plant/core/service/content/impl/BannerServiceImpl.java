package com.moguying.plant.core.service.content.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.dao.content.BannerDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.Banner;
import com.moguying.plant.core.service.content.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDAO bannerDAO;


    @Override
    @DS("read")
    public PageResult<Banner> bannerList(Integer page, Integer size, Banner where) {
        LambdaQueryWrapper<Banner> query = new QueryWrapper<Banner>().lambda()
                .isNotNull(Banner::getId)
                .eq(Banner::getId, where.getId())
                .isNotNull(Banner::getName)
                .like(Banner::getName, where.getName())
                .isNotNull(Banner::getIsShow)
                .eq(Banner::getIsShow, where.getIsShow());

        IPage<Banner> pageResult = bannerDAO.selectPage(new Page<>(page, size), query);
        return new PageResult<>(pageResult.getTotal(),pageResult.getRecords());
    }

    @Override
    @DS("write")
    public ResultData<Integer> addBanner(Banner banner) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        Banner where = new Banner();
        where.setName(banner.getName());
        Integer count = bannerDAO.selectCount(new QueryWrapper<>(where));
        if(null != count  && count > 0)
            return resultData.setMessageEnum(MessageEnum.BANNER_NAME_EXISTS);
        banner.setAddTime(new Date());
        if(bannerDAO.insert(banner) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(banner.getId());
        return resultData;
    }

    @Override
    @DS("write")
    public ResultData<Integer> updateBanner(Integer id, Banner update) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        if(bannerDAO.selectById(id) == null)
            return resultData.setMessageEnum(MessageEnum.BANNER_NOT_EXISTS);
        Banner where = new Banner();
        where.setName(update.getName());
        Integer count  = bannerDAO.selectCount(new QueryWrapper<>(where));
        if(null != count && count > 0)
            return resultData.setMessageEnum(MessageEnum.BANNER_NAME_EXISTS);

        update.setId(id);
        if(bannerDAO.updateById(update) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(id);
        return resultData;
    }

    @Override
    @DS("write")
    public Integer deleteBanner(Integer id) {
        if(bannerDAO.deleteById(id) > 0)
            return id;
        return -1;
    }

    @Override
    @DS("write")
    public Boolean setBannerShowState(Integer id) {
        Banner banner = bannerDAO.selectById(id);
        if(banner == null)
            return false;
        Banner update = new Banner();
        update.setId(id);
        update.setIsShow(!banner.getIsShow());
        return bannerDAO.updateById(update) > 0 ? update.getIsShow() : false;
    }

    @Override
    @DS("read")
    public List<Banner> listForHome(Integer type) {
        return bannerDAO.bannerListForHome(type);
    }
}
