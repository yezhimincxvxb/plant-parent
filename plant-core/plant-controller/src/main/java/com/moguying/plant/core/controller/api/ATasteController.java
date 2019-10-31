package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.seed.vo.PlantOrder;
import com.moguying.plant.core.entity.seed.vo.PlantOrderResponse;
import com.moguying.plant.core.entity.taste.Taste;
import com.moguying.plant.core.entity.taste.TasteApply;
import com.moguying.plant.core.entity.taste.vo.TasteReap;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.teste.TasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taste")
public class ATasteController {

    @Autowired
    private TasteService tasteService;

    @Autowired
    private PlantOrderService plantOrderService;


    /**
     * 购买体验
     *
     * @param userId
     * @param buyOrder
     * @return
     */
    @PostMapping("/buy")
    public ResponseData<BuyOrderResponse> buy(@LoginUserId Integer userId, @RequestBody BuyOrder buyOrder) {
        ResultData<BuyOrderResponse> buyResult = tasteService.buy(buyOrder, userId);
        return new ResponseData<>(buyResult.getMessageEnum().getMessage(), buyResult.getMessageEnum().getState(), buyResult.getData());
    }


    /**
     * 种植体验
     *
     * @return
     */
    public ResponseData<PlantOrderResponse> plant(@LoginUserId Integer userId, @RequestBody PlantOrder plantOrder) {
        ResultData<TriggerEventResult<PlantOrderResponse>> resultData = plantOrderService.plantSeed(userId, plantOrder, true);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 采摘并出售
     *
     * @return
     */
    @PostMapping("/{orderId}")
    public ResponseData<Boolean> reapAndSale(@LoginUserId Integer userId, @PathVariable Integer orderId) {
        ResultData<TasteReap> resultData = tasteService.reap(userId, orderId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 首页免费试吃表列
     *
     * @param search
     * @return
     */
    @NoLogin
    @PostMapping("/free/list")
    public PageResult<Taste> freeTasteList(@RequestBody PageSearch<Taste> search) {
        return tasteService.tastePageResult(search.getPage(), search.getSize(), search.getWhere());
    }


    /**
     * 用户登录查询的试吃列表
     * @param userId
     * @param search
     * @return
     */
    @PostMapping("/free")
    public PageResult<Taste> freeTasteList(@LoginUserId Integer userId, @RequestBody PageSearch<Taste> search) {
        return tasteService.tastePageResult(search.getPage(),search.getSize(),search.getWhere(),userId);
    }


    /**
     * 查询申请记录
     * @param search
     * @return
     */
    @PostMapping("/free/apply/log")
    public PageResult<TasteApply> tasteApplyList(@RequestBody PageSearch<TasteApply> search){
        return tasteService.tasteApplyPageResult(search.getPage(),search.getSize(),search.getWhere());
    }




    /**
     * 申请免费资格
     *
     * @param userId
     * @param taste
     * @return
     */
    @PostMapping("/free/apply")
    public ResponseData<Boolean> applyFreeTaste(@LoginUserId Integer userId, @RequestBody Taste taste) {
        ResultData<Boolean> resultData = tasteService.addTasteApply(userId, taste);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 查询个人申请资格通过情况
     *
     * @param userId
     * @param taste
     * @return
     */
    @GetMapping("/free/check")
    public ResponseData<TasteApply> checkTasteApply(@LoginUserId Integer userId, @RequestBody Taste taste) {
        ResultData<TasteApply> tasteApplyResultData = tasteService.checkApply(userId, taste);
        ResponseData<TasteApply> responseData = new ResponseData<>(tasteApplyResultData.getMessageEnum().getMessage(), tasteApplyResultData.getMessageEnum().getState());
        if (tasteApplyResultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            responseData.setData(tasteApplyResultData.getData());
        }
        return responseData;
    }


}
