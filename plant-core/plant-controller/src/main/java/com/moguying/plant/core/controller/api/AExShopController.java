package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.ExShopPic;
import com.moguying.plant.core.entity.exshop.vo.ExShopVo;
import com.moguying.plant.core.service.exshop.ExShopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exshop")
public class AExShopController {

    @Autowired
    private ExShopService exShopService;

    /**
     * 体验店列表
     */
    @PostMapping("/list")
    @ApiOperation("体验店列表")
    public PageResult<ExShopVo> exShopPageResult(@RequestBody PageSearch<ExShop> search) {
        return exShopService.exShopPageResult(search.getPage(), search.getSize(), search.getWhere(), true);
    }

    /**
     * 体验店详情
     */
    @GetMapping("/{shopId}")
    @ApiOperation("体验店详情")
    public ResponseData<ExShopVo> exShopPageResult(@PathVariable("shopId") Integer shopId) {
        ResponseData<ExShopVo> responseData = new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return responseData.setData(exShopService.getExShopById(shopId));
    }

    /**
     * 查询对应体验店的轮播图
     */
    @PostMapping("/banner")
    @ApiOperation("体验店轮播图")
    public ResponseData<List<ExShopPic>> findShopPic(@RequestBody ExShopPic shopPic) {
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), exShopService.showPicList(shopPic));
    }

}