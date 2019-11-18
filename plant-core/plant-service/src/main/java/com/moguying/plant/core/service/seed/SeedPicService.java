package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.seed.SeedPic;

import java.util.List;

public interface SeedPicService {


    PageResult<SeedPic> seedPicList(Integer page, Integer size);


    SeedPic seedPicDelete(Long id);


    int seePicAdd(SeedPic seedPic);


    List<SeedPic> seedPic(SeedPic seedPic);


    List<SeedPic> seedPicByRange(List<Integer> ids);


    SeedPic seedPicById(Long id);

}
