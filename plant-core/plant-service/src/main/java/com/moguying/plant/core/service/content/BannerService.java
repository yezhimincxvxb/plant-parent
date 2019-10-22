package com.moguying.plant.core.service.content;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.Banner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BannerService {

    @DataSource("read")
    PageResult<Banner> bannerList(Integer page, Integer size, Banner where);

    @DataSource("write")
    ResultData<Integer> addBanner(Banner banner);

    @DataSource("write")
    ResultData<Integer> updateBanner(Integer id, Banner banner);

    @DataSource("write")
    Boolean setBannerShowState(Integer id);

    @DataSource("write")
    Integer deleteBanner(Integer id);

    @DataSource("read")
    List<Banner> listForHome(Integer type);

}
