package com.moguying.plant.core.service.bargain;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.vo.BargainResponse;
import com.moguying.plant.core.entity.bargain.vo.SendNumber;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.mall.vo.BuyProduct;
import com.moguying.plant.core.entity.user.UserAddress;

import java.util.List;

public interface BargainDetailService {

    BargainDetail getOneByOpen(Integer userId, Integer productId, Boolean state);

    BargainDetail getOneByClose(Integer userId, Integer productId, Boolean state);

    BargainDetail shareSuccess(Integer userId, BuyProduct buyProduct, MallProduct product);

    Boolean helpSuccess(Integer userId, BargainLog bargainLog, BargainDetail detail);

    PageResult<BargainResponse> successLogs(Integer page, Integer size);

    List<SendNumber> sendNumber();

    PageResult<BargainResponse> doingList(Integer page, Integer size, Integer userId);

    BargainResponse productInfo(Integer userId, Integer id);

    PageResult<BargainResponse> ownLog(Integer page, Integer size, Integer userId);

    MallOrder submitOrder(Integer userId, Integer productId);

    Boolean submitSuccess(Integer userId, MallProduct product, UserAddress address, BargainDetail detail, MallOrder order);

    Integer getNumber(Integer productId);
}
