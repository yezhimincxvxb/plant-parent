package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.Seed;
import com.moguying.plant.core.entity.dto.SeedReview;
import com.moguying.plant.core.entity.vo.HomeSeed;
import com.moguying.plant.core.entity.vo.SeedDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SeedService {

    @DataSource("read")
    PageResult<Seed> seedList(Integer page, Integer size, Seed seed);

    @DataSource("write")
    ResultData<Integer> review(Integer id, SeedReview seedReview, Integer verifyUserId);

    @DataSource("write")
    ResultData<Integer> seedSave(Seed seed, boolean isAdd);
    //后台菌包详情
    @DataSource("read")
    Seed seed(Integer id);

    @DataSource("read")
    Integer seedFull(Integer id);

    @DataSource("write")
    Boolean seedShow(Integer id);

    @DataSource("read")
    PageResult<HomeSeed> seedListForHome(Integer page, Integer size);

    @DataSource("read")
    ResultData<Integer> seedCancel(Integer id);

    //前端及app菌包详情
    @DataSource("read")
    SeedDetail seedDetail(Integer id);

    @DataSource("read")
    List<HomeSeed> recommendSeed();

    //选一个最近售罄菌包
    @DataSource("read")
    HomeSeed selectOneSaleDownSeed();

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<Seed> search, HttpServletRequest request);

}

