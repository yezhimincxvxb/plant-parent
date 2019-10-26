package com.moguying.plant.core.service.content.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
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
@CacheConfig(cacheNames = "banner")
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDAO bannerDAO;


    @Override
    @DS("read")
    public PageResult<Banner> bannerList(Integer page, Integer size, Banner where) {
        bannerDAO.selectSelective(where);
        return null;
    }

    @Override
    @DS("write")
    @CachePut(key = "#banner.id")
    public ResultData<Integer> addBanner(Banner banner) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        Banner where = new Banner();
        where.setName(banner.getName());
        List<Banner> banners = bannerDAO.selectSelective(where);
        if(banners.size() > 0)
            return resultData.setMessageEnum(MessageEnum.BANNER_NAME_EXISTS);
        banner.setAddTime(new Date());
        if(bannerDAO.insert(banner) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(banner.getId());
        return resultData;
    }

    @Override
    @DS("write")
    @CachePut(key = "#update.id")
    public ResultData<Integer> updateBanner(Integer id, Banner update) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,0);
        if(bannerDAO.selectById(id) == null)
            return resultData.setMessageEnum(MessageEnum.BANNER_NOT_EXISTS);
        Banner where = new Banner();
        where.setName(update.getName());
        List<Banner> banners = bannerDAO.selectSelective(where);
        for(Banner banner : banners){
            if(!banner.getId().equals(id))
               return resultData.setMessageEnum(MessageEnum.BANNER_NAME_EXISTS);
        }

        update.setId(id);
        if(bannerDAO.updateById(update) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(id);
        return resultData;
    }

    @Override
    @DS("write")
    @CacheEvict(key = "#id")
    public Integer deleteBanner(Integer id) {
        if(bannerDAO.deleteById(id) > 0)
            return id;
        return -1;
    }

    @Override
    @DS("write")
    @CachePut(key = "#id")
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
    @Cacheable(key = "'type:'+ #type")
    public List<Banner> listForHome(Integer type) {
        return bannerDAO.bannerListForHome(type);
    }
}
