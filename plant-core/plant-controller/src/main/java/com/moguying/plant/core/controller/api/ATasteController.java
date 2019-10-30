package com.moguying.plant.core.controller.api;

import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.seed.vo.PlantOrder;
import com.moguying.plant.core.entity.seed.vo.PlantOrderResponse;
import com.moguying.plant.core.entity.taste.vo.TasteReap;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.reap.ReapService;
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

    @Autowired
    private ReapService reapService;



    /**
     * 购买体验
     * @param userId
     * @param buyOrder
     * @return
     */
    @PostMapping("/buy")
    public ResponseData<BuyOrderResponse> buy(@LoginUserId Integer userId, @RequestBody BuyOrder buyOrder) {
        ResultData<BuyOrderResponse> buyResult = tasteService.buy(buyOrder, userId);
        return new ResponseData<>(buyResult.getMessageEnum().getMessage(),buyResult.getMessageEnum().getState(),buyResult.getData());
    }


    /**
     * 种植体验
     * @return
     */
    public ResponseData<PlantOrderResponse> plant(@LoginUserId Integer userId, @RequestBody PlantOrder plantOrder){
        ResultData<TriggerEventResult<PlantOrderResponse>> resultData = plantOrderService.plantSeed(userId, plantOrder,true);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 采摘并出售
     * @return
     */
    @PostMapping("/{orderId}")
    public ResponseData<Boolean> reapAndSale(@LoginUserId Integer userId, @PathVariable Integer orderId) {
        ResultData<TasteReap> resultData = tasteService.reap(userId,orderId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
    }


}
