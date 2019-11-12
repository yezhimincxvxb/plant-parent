package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.seed.vo.BuyOrder;
import com.moguying.plant.core.entity.seed.vo.BuyOrderResponse;
import com.moguying.plant.core.entity.seed.vo.PlantOrder;
import com.moguying.plant.core.entity.seed.vo.PlantOrderResponse;
import com.moguying.plant.core.entity.taste.PopMessage;
import com.moguying.plant.core.entity.taste.Taste;
import com.moguying.plant.core.entity.taste.TasteApply;
import com.moguying.plant.core.entity.taste.vo.TasteReap;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.teste.PopMessageService;
import com.moguying.plant.core.service.teste.TasteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taste")
@Api(tags = "免费试吃")
public class ATasteController {

    @Autowired
    private TasteService tasteService;

    @Autowired
    private PlantOrderService plantOrderService;

    @Autowired
    private PopMessageService popMessageService;

    /**
     * 用户是否新手
     */
    @GetMapping("/is/new")
    @ApiOperation("用户是否新手")
    public ResponseData<Boolean> isNew(@LoginUserId Integer userId) {
        ResponseData<Boolean> responseData = new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());

        if (Objects.isNull(userId))
            return responseData
                    .setMessage(MessageEnum.PARAMETER_ERROR.getMessage())
                    .setState(MessageEnum.PARAMETER_ERROR.getState());

       return responseData.setData(tasteService.isNew(userId));
    }

    /**
     * 购买体验
     *
     * @param userId
     * @param buyOrder
     * @return
     */
    @PostMapping("/buy")
    @ApiOperation("购买体验")
    public ResponseData<BuyOrderResponse> buy(@LoginUserId Integer userId, @RequestBody BuyOrder buyOrder) {
        ResultData<BuyOrderResponse> buyResult = tasteService.buy(buyOrder, userId);
        return new ResponseData<>(buyResult.getMessageEnum().getMessage(), buyResult.getMessageEnum().getState(), buyResult.getData());
    }


    /**
     * 种植体验
     *
     * @return
     */
    @PostMapping("/plant")
    @ApiOperation("种植体验")
    public ResponseData<PlantOrderResponse> plant(@LoginUserId Integer userId, @RequestBody PlantOrder plantOrder) {
        ResultData<TriggerEventResult<PlantOrderResponse>> resultData = plantOrderService.plantSeed(userId, plantOrder, true);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(),resultData.getData() == null ? null : resultData.getData().getData());
    }


    /**
     * 采摘并出售
     *
     * @return
     */
    @PostMapping("/reap/{reapId}")
    @ApiOperation("采摘并出售")
    public ResponseData<TasteReap> reapAndSale(@LoginUserId Integer userId, @PathVariable Integer reapId) {
        ResultData<TasteReap> resultData = tasteService.reap(userId, reapId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 获取使用的弹幕
     * @return
     */
    @GetMapping("/pop")
    @ApiOperation("获取使用的弹幕")
    @NoLogin
    public ResponseData<PopMessage> popMessage(){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),popMessageService.usedPopMessage());
    }


    /**
     *  新手体验券礼包
     * @return
     */
    @GetMapping("/gift")
    @ApiOperation("新手体验券礼包")
    @NoLogin
    public ResponseData<List<Fertilizer>> giftList(){
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),
                tasteService.tasteGiftList());
    }

    /**
     * 领取新手礼包
     * @param userId
     * @return
     */
    @PostMapping("/gift")
    @ApiOperation("领取新手礼包")
    public ResponseData<Integer> pickUpGift(@LoginUserId Integer userId){
        ResultData<Integer> pickUpGift = tasteService.pickUpGift(userId);
        return new ResponseData<>(pickUpGift.getMessageEnum().getMessage(),pickUpGift.getMessageEnum().getState(), pickUpGift.getData());
    }




    /**
     * 首页免费试吃表列
     *
     * @param search
     * @return
     */
    @NoLogin
    @PostMapping("/free/list")
    @ApiOperation("首页免费试吃表列")
    public PageResult<Taste> freeTasteList(@RequestBody PageSearch<Taste> search) {
      return freeTasteList(null,search);
    }


    /**
     * 用户登录查询的试吃列表
     * @param userId
     * @param search
     * @return
     */
    @PostMapping("/free")
    @ApiOperation("用户登录查询的试吃列表")
    public PageResult<Taste> freeTasteList(@LoginUserId Integer userId, @RequestBody PageSearch<Taste> search) {
        Optional<Taste> optional = Optional.ofNullable(search.getWhere());
        if(!optional.isPresent())
            search.setWhere(new Taste());
        search.getWhere().setIsShow(true);
        return tasteService.tastePageResult(search.getPage(),search.getSize(),search.getWhere(),userId);
    }


    /**
     * 查询申请记录
     * @param search
     * @return
     */
    @NoLogin
    @PostMapping("/free/apply/log")
    @ApiOperation("查询申请记录")
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
    @ApiOperation("申请免费资格")
    public ResponseData<Boolean> applyFreeTaste(@LoginUserId Integer userId, @RequestBody(required = false) Taste taste) {
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
    @PostMapping("/free/check")
    @ApiOperation("查询个人申请资格通过情况")
    public ResponseData<TasteApply> checkTasteApply(@LoginUserId Integer userId, @RequestBody Taste taste) {
        ResultData<TasteApply> tasteApplyResultData = tasteService.checkApply(userId, taste);
        ResponseData<TasteApply> responseData = new ResponseData<>(tasteApplyResultData.getMessageEnum().getMessage(), tasteApplyResultData.getMessageEnum().getState());
        if (tasteApplyResultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            responseData.setData(tasteApplyResultData.getData());
        }
        return responseData;
    }


}
