package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.dto.SeedInnerBuy;
import com.moguying.plant.core.entity.dto.SeedInnerOrderCount;
import com.moguying.plant.core.entity.dto.UserInner;

import java.util.List;

public interface SeedInnerService {
    PageResult<SeedInnerOrderCount> seedInnerList(Integer page, Integer size);
    List<UserInner> seedInnerUserList(Integer seedId, Integer innerCount, Integer userCount);
    Integer seedInnerOrder(SeedInnerBuy seedInnerBuy);
}
