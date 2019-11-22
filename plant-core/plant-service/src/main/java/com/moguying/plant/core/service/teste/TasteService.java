package com.moguying.plant.core.service.teste;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.taste.Taste;
import com.moguying.plant.core.entity.taste.TasteApply;
import com.moguying.plant.core.entity.taste.vo.TasteReap;

import java.util.List;

public interface TasteService {

    Boolean isNew(Integer userId);

    /**
     * 购买体验包
     * @param buyOrder
     * @param userId
     * @return
     */
    ResultData<BuyOrderResponse> buy(BuyOrder buyOrder, Integer userId);

    /**
     * 体验包采摘
     */
    ResultData<TasteReap> reap(Integer userId, Integer reapId);

    /**
     * 添加免费试吃项
     * @param taste
     * @return
     */
    ResultData<Boolean> saveTaste(Taste taste);


    /**
     * 免费试吃项列表
     * @param page
     * @param size
     * @param where
     * @return
     */
    PageResult<Taste> tastePageResult(Integer page,Integer size,Taste where);

    PageResult<Taste> tastePageResult(Integer page,Integer size,Taste where,Integer userId);

    /**
     * 删除试吃项
     * @param id
     * @return
     */
    ResultData<Boolean> deleteTaste(String id);


    /**
     * 设置试吃项上下架
     * @param id
     * @return
     */
    Boolean setShowState(String id);

    Boolean setState(TasteApply apply);


    /**
     * 添加试吃申请
     * @param userId
     * @param taste
     * @return
     */
    ResultData<Boolean> addTasteApply(Integer userId,Taste taste);


    /**
     * 查询申请状态
     * @param userId
     * @param taste
     * @return
     */
    ResultData<TasteApply> checkApply(Integer userId, Taste taste);

    /**
     * 查询申请记录
     * @param page
     * @param size
     * @param where
     * @return
     */
    PageResult<TasteApply> tasteApplyPageResult(Integer page,Integer size,TasteApply where);


    /**
     * 后台申请记录列表
     * @param page
     * @param size
     * @param where
     * @return
     */
    PageResult<TasteApply> bTasteApplyPageResult(Integer page, Integer size, TasteApply where);

    /**
     * 新手体验礼包券
     * @return
     */
    List<Fertilizer> tasteGiftList();

    /**
     *  领取礼包
     * @param userId
     * @return
     */
    ResultData<Integer> pickUpGift(java.lang.Integer userId);


}
