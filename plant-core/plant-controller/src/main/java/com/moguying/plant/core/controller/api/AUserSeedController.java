package com.moguying.plant.core.controller.api;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.mall.MallProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userSeed")
@Api(tags = "卖场中心")
public class AUserSeedController {

    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * 兑换列表 (不需要用户登录)
     */
    @PostMapping("/exchangeList")
    @ApiOperation("兑换列表")
    public PageResult<ExchangeInfo> exchangeList(@RequestBody PageSearch<Integer> pageSearch) {
        Integer page = pageSearch.getPage();
        Integer size = pageSearch.getSize();
        Integer where = pageSearch.getWhere();
        // 根据where，显示不同的列表
        PageResult<ExchangeInfo> result = null;
        if (where == 2) {
            result = mallProductService.showProducts(page, size);
        } else if (where == 3) {
            result = fertilizerService.showFertilizer(page, size);
        }
        return result;
    }
}
