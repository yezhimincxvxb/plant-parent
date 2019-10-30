package com.moguying.plant.core.service.teste.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.ReapEnum;
import com.moguying.plant.core.dao.reap.ReapDAO;
import com.moguying.plant.core.dao.seed.SeedDAO;
import com.moguying.plant.core.dao.seed.SeedOrderDetailDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.seed.Seed;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.taste.vo.TasteReap;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.teste.TasteService;
import com.moguying.plant.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasteServiceImpl implements TasteService {


    @Autowired
    private SeedDAO seedDAO;


    @Autowired
    private PlantOrderService plantOrderService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SeedOrderDetailDAO seedOrderDetailDAO;

    @Autowired
    private ReapDAO reapDAO;

    @DS("write")
    @Override
    public ResultData<BuyOrderResponse> buy(BuyOrder buyOrder,Integer userId) {
        ResultData<BuyOrderResponse> resultData = new ResultData<>(MessageEnum.ERROR,null);
        Seed seed = seedDAO.seedInfoWithTypeById(buyOrder.getSeedId());
        if(null == seed)
            return resultData.setMessageEnum(MessageEnum.SEED_NOT_EXISTS);
        if(null == seed.getTypeInfo())
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_EXIST);
        if(!seed.getTypeInfo().getIsForNew())
            return resultData.setMessageEnum(MessageEnum.SEED_TYPE_NOT_FOR_TASTE);
        //TODO 是否已参于过
        ResultData<BuyOrderResponse> buyResult = plantOrderService.plantOrder(buyOrder, userId, true);
        if(!buyResult.getMessageEnum().equals(MessageEnum.SUCCESS))
            return resultData.setMessageEnum(buyResult.getMessageEnum());
        ResultData<Integer> payResult =
                plantOrderService.payOrderSuccess(seedOrderDetailDAO.selectById(buyResult.getData().getOrderId()),
                        userDAO.selectById(userId));
        if(payResult.getMessageEnum().equals(MessageEnum.SUCCESS))
            return buyResult;
        return resultData;
    }

    @Override
    public ResultData<TasteReap> reap(Integer userId, Integer orderId) {
        ResultData<TasteReap> resultData = new ResultData<>(MessageEnum.ERROR, null);
        Reap where = new Reap();
        where.setUserId(userId);
        where.setOrderId(orderId);
        Reap reap = reapDAO.selectOne(new QueryWrapper<>(where));
        if (null == reap)
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_EXISTS);
        if (reap.getPreReapTime().getTime() != DateUtil.INSTANCE.todayEnd().getTime())
            return resultData.setMessageEnum(MessageEnum.SEED_REAP_NOT_IN_TIME);
        Reap update = new Reap();
        update.setId(reap.getId());
        update.setState(ReapEnum.REAP_DONE.getState());
        if(reapDAO.updateById(update) > 0) {
            TasteReap tasteReap = new TasteReap();
            tasteReap.setPlantWeigh(reap.getPlantWeigh());
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(tasteReap);
        }
        return resultData;
    }










}
