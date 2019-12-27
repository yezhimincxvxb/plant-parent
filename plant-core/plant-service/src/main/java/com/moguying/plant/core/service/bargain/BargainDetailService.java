package com.moguying.plant.core.service.bargain;


import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.BargainRate;
import com.moguying.plant.core.entity.bargain.vo.BackBargainDetailVo;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.entity.bargain.vo.ShareVo;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.user.UserAddress;

import java.util.List;

public interface BargainDetailService {

    BargainDetail getOneByClose(Integer userId, Integer productId, Boolean state);

    ResultData<ShareVo> shareSuccess(Integer userId, MallProduct product, BargainRate bargainRate);

    BargainDetail getOneById(Integer id);

    BargainLog helpSuccess(Integer userId, BargainDetail detail);

    PageResult<BargainVo> successLogs(Integer page, Integer size);

    List<SendNumberVo> sendNumber();

    PageResult<BargainVo> doingList(Integer page, Integer size, Integer userId, Boolean isSuccess);

    BargainVo productInfoByOrderId(BargainVo bargainVo);

    PageResult<BargainVo> ownLog(Integer page, Integer size, Integer userId);

    MallOrder submitOrder(Integer userId, Integer productId);

    Boolean submitSuccess(Integer userId, MallProduct product, UserAddress address, BargainDetail detail, MallOrder order);

    Integer getNumber(Integer productId);

    PageResult<BackBargainDetailVo> bargainList(Integer page, Integer size, BargainVo bargainVo);

    BargainRate getBargainRate(Integer productId);

    Boolean bargainOrderClose(Integer id);

    List<BargainDetail> needCloseList();

}
