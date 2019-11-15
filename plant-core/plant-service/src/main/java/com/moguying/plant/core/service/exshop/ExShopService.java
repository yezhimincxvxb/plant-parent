package com.moguying.plant.core.service.exshop;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.exshop.ExShop;
import com.moguying.plant.core.entity.exshop.ExShopPic;

import java.util.List;

public interface ExShopService {

    ResultData<String> saveExShop(ExShop exShop);


    PageResult<ExShop> exShopPageResult(Integer page, Integer size, ExShop search);

    List<ExShopPic> showPicList(ExShopPic search);


}
