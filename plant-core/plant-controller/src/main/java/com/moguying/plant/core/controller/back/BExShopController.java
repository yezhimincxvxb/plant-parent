package com.moguying.plant.core.controller.back;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.vo.ExShopVo;
import com.moguying.plant.core.service.exshop.ExShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exshop")
public class BExShopController {

    @Autowired
    private ExShopService exShopService;

    /**
     * 体验店列表
     */
    @PostMapping("/list")
    @ApiOperation("体验店列表")
    public PageResult<ExShopVo> exShopPageResult(@RequestBody PageSearch<ExShop> search) {
        return exShopService.exShopPageResult(search.getPage(), search.getSize(), search.getWhere(), false);
    }

    /**
     * 添加/修改体验店
     */
    @PostMapping
    @ApiOperation("添加/修改体验店")
    public ResponseData<String> saveExShop(@RequestBody ExShop exShop) {
        ResultData<String> resultData = exShopService.saveExShop(exShop);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }

}