package com.moguying.plant.core.service.seed;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.dto.SeedOrder;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;
import com.moguying.plant.core.entity.vo.CanPlantOrder;
import com.moguying.plant.core.entity.vo.UserSeedOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SeedOrderService {

    @DataSource("read")
    PageResult<SeedOrder> seedOrderList(Integer page, Integer size, SeedOrder where);

    @DataSource("write")
    Boolean incrSeedOrder(SeedOrderDetail seedOrderDetail);

    @DataSource("read")
    CanPlantOrder sumUserSeedByBlockId(Integer blockId, Integer userId);

    @DataSource("read")
    List<UserSeedOrder> userSeedOrder(Integer userId);

    @DataSource("read")
    void downloadExcel(Integer userId, PageSearch<SeedOrder> search, HttpServletRequest request);
}
