package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.ExShopPic;
import com.moguying.plant.core.service.exshop.ExShopService;
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
     * @param search
     * @return
     */
    @PostMapping
    private PageResult<ExShop> exShopPageResult(@RequestBody PageSearch<ExShop> search) {
        return exShopService.exShopPageResult(search.getPage(),search.getSize(),search.getWhere());
    }

    /**
     * 查询对应体验店的轮播图
     * @param shopPic
     * @return
     */
    @PostMapping("/banner")
    private ResponseData<List<ExShopPic>> findShopPic(@RequestBody ExShopPic shopPic){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),exShopService.showPicList(shopPic));
    }

}
