package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.SeedPic;

import java.util.List;

public interface SeedPicService {

    @DataSource("read")
    PageResult<SeedPic> seedPicList(Integer page, Integer size);

    @DataSource("write")
    SeedPic seedPicDelete(Long id);

    @DataSource("write")
    int seePicAdd(SeedPic seedPic);

    @DataSource("read")
    List<SeedPic> seedPic(SeedPic seedPic);

    @DataSource("read")
    List<SeedPic> seedPicByRange(List<Integer> ids);

    @DataSource("read")
    SeedPic  seedPicById(Long id);

}
