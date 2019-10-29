package com.moguying.plant.core.service.bargain;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.vo.BuyProduct;
import com.moguying.plant.core.entity.user.UserAddress;

import java.util.List;

public interface BargainDetailService {

    BargainDetail getOneByOpen(Integer userId, Integer productId, Boolean state);

    BargainDetail getOneByClose(Integer userId, Integer productId, Boolean state);

    BargainDetail shareSuccess(Integer userId, BuyProduct buyProduct, MallProduct product);

    BargainDetail getOneById(Integer id);

    Boolean helpSuccess(Integer userId, BargainDetail detail);

    PageResult<BargainVo> successLogs(Integer page, Integer size);

    List<SendNumberVo> sendNumber();

    PageResult<BargainVo> doingList(Integer page, Integer size, Integer userId);

    BargainVo productInfo(Integer userId, Integer orderId);

    PageResult<BargainVo> ownLog(Integer page, Integer size, Integer userId);

    MallOrder submitOrder(Integer userId, Integer productId);

    Boolean submitSuccess(Integer userId, MallProduct product, UserAddress address, BargainDetail detail, MallOrder order);

    Integer getNumber(Integer productId);
}