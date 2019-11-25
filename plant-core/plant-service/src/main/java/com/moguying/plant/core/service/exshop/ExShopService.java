package com.moguying.plant.core.service.exshop;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.ExShopPic;
import com.moguying.plant.core.entity.exshop.vo.ExShopVo;

import java.util.List;

public interface ExShopService {

    ResultData<String> saveExShop(ExShop exShop);

    PageResult<ExShopVo> exShopPageResult(Integer page, Integer size, ExShop search, Boolean showPic);

    List<ExShopPic> showPicList(ExShopPic search);

    ExShopVo getExShopById(Integer shopId);
}