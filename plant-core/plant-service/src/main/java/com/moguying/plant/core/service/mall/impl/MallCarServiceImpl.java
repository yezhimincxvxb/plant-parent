package com.moguying.plant.core.service.mall.impl;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.annotation.Pagination;
import com.moguying.plant.core.dao.mall.MallCarDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.vo.MallCar;
import com.moguying.plant.core.entity.vo.OrderItem;
import com.moguying.plant.core.service.mall.MallCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MallCarServiceImpl implements MallCarService {

    @Autowired
    MallCarDAO mallCarDAO;

    @Autowired
    MallProductDAO mallProductDAO;


    @Pagination
    @Override
    @DataSource("read")
    public PageResult<OrderItem> userCarItems(Integer page, Integer size, Integer userId) {
        mallCarDAO.userCarItemList(userId);
        return null;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> addItemToCar(MallCar mallCar) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(null == mallProductDAO.productCountEnough(mallCar.getProductId(),mallCar.getProductCount()))
            return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_ENOUGH);
        MallCar carItem =  mallCarDAO.selectByUserIdAndProductId(mallCar.getUserId(),mallCar.getProductId());
        if(null == carItem){
            if(mallCarDAO.insert(mallCar) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(mallCar.getId());
        } else {
            if(mallCarDAO.addCarItemCount(carItem.getId(),mallCar.getProductCount()) > 0)
                return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(carItem.getId());
        }
        return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<Boolean> removeItemFromCar(List<OrderItem> ids,Integer userId) {
        ResultData<Boolean> resultData = new ResultData<>(MessageEnum.ERROR,false);
        if(mallCarDAO.deleteItemByRange(ids,userId) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS).setData(true);
        return resultData;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> modifyItemCount(OrderItem orderItem) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        MallCar item = mallCarDAO.selectById(orderItem.getId());
        if(null == item || !orderItem.getProductId().equals(item.getProductId()))
            return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_EXISTS);
        Integer modifyCount = orderItem.getBuyCount();
        if(modifyCount > 0) {
            if (null == mallProductDAO.productCountEnough(item.getProductId(), modifyCount))
                return resultData.setMessageEnum(MessageEnum.MALL_PRODUCT_NOT_ENOUGH);
        } else {
            return resultData.setMessageEnum(MessageEnum.CAR_ITEM_LESS_ONE);
        }
        MallCar update = new MallCar();
        update.setId(orderItem.getId());
        update.setProductCount(orderItem.getBuyCount());
        if(mallCarDAO.updateById(update) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Override
    @DataSource("read")
    public BigDecimal getCheckedItemAmount(Integer userId) {
        BigDecimal amount = mallCarDAO.sumCheckedItemAmount(userId);
       if(null == amount)
           return BigDecimal.ZERO;
       return amount;
    }

    @Override
    @DataSource("write")
    public ResultData<Integer> checkItems(List<OrderItem> items,Integer userId,Boolean check) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR,null);
        if(mallCarDAO.checkItems(items,userId,check) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Override
    @DataSource("read")
    public Boolean isAllCheck(Integer userId) {
        Integer unCheckCount = mallCarDAO.countByCheckState(false,userId);
        if(null == unCheckCount || unCheckCount == 0)
            return true;
        return false;
    }
}
