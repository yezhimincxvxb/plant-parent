package com.moguying.plant.core.service.content;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.content.Banner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BannerService {


    PageResult<Banner> bannerList(Integer page, Integer size, Banner where);


    ResultData<Integer> addBanner(Banner banner);


    ResultData<Integer> updateBanner(Integer id, Banner banner);


    Boolean setBannerShowState(Integer id);


    Integer deleteBanner(Integer id);


    List<Banner> listForHome(Integer type);

}
