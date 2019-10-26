package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.seed.SeedOrder;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.CanPlantOrder;
import com.moguying.plant.core.entity.user.vo.UserSeedOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SeedOrderService {

    
    PageResult<SeedOrder> seedOrderList(Integer page, Integer size, SeedOrder where);

    
    Boolean incrSeedOrder(SeedOrderDetail seedOrderDetail);

    
    CanPlantOrder sumUserSeedByBlockId(Integer blockId, Integer userId);

    
    List<UserSeedOrder> userSeedOrder(Integer userId);

    
    void downloadExcel(Integer userId, PageSearch<SeedOrder> search, HttpServletRequest request);
}
