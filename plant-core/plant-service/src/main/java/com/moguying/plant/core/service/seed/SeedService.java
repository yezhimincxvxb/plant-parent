package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.common.vo.HomeSeed;
import com.moguying.plant.core.entity.index.SeedDetailTable;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.vo.SeedDetail;
import com.moguying.plant.core.entity.seed.vo.SeedReview;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SeedService {


    PageResult<Seed> seedList(Integer page, Integer size, Seed seed);


    ResultData<Integer> review(Integer id, SeedReview seedReview, Integer verifyUserId);


    ResultData<Integer> seedSave(Seed seed, boolean isAdd);
    //后台菌包详情

    Seed seed(Integer id);


    Integer seedFull(Integer id);


    Boolean seedShow(Integer id);


    PageResult<HomeSeed> seedListForHome(Integer page, Integer size, HomeSeed where);


    ResultData<Integer> seedCancel(Integer id);

    //前端及app菌包详情

    SeedDetail seedDetail(Integer id);


    List<HomeSeed> recommendSeed();

    //选一个最近售罄菌包

    HomeSeed selectOneSaleDownSeed();


    void downloadExcel(Integer userId, PageSearch<Seed> search, HttpServletRequest request);

    List<SeedDetailTable> seedDetailList();
}

