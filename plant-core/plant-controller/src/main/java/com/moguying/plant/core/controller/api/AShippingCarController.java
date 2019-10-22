package com.moguying.plant.core.controller.api;

import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.constant.MessageEnum;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.vo.*;
import com.moguying.plant.core.service.mall.MallCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/mall/car")
public class AShippingCarController {


    @Autowired
    private MallCarService mallCarService;



    /**
     * 添加入购物车
     */
    @PostMapping
    @ResponseBody
    public ResponseData<Integer> addToCar(@RequestBody BuyProduct itemToCar, @LoginUserId Integer userId){
        MallCar mallCar = new MallCar();
        mallCar.setUserId(userId);
        mallCar.setProductCount(itemToCar.getBuyCount());
        mallCar.setProductId(itemToCar.getProductId());
        ResultData<Integer> resultData = mallCarService.addItemToCar(mallCar);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState(),resultData.getData());
    }


    /**
     * 购物车列表
     */
    @GetMapping
    @ResponseBody
    public ResponseData<CarItemList> carItems(@LoginUserId Integer userId, @RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "size",defaultValue = "10") Integer size
                                         ){

        CarItemList carItemList = new CarItemList();
        PageResult<OrderItem> pageResult = mallCarService.userCarItems(page,size,userId);
        carItemList.setCheckedAmount(mallCarService.getCheckedItemAmount(userId));
        carItemList.setItems(pageResult.getData());
        carItemList.setCount(pageResult.getCount());
        carItemList.setCheckAll(mallCarService.isAllCheck(userId));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(),MessageEnum.SUCCESS.getState(),carItemList);
    }


    /**
     * 删除购物车中的商品
     */
    @PostMapping("/delete")
    @ResponseBody
    public ResponseData<ModifyItemResponse> deleteItems(@LoginUserId Integer userId, @RequestBody List<OrderItem> ids){
        ResultData<Boolean> resultData = mallCarService.removeItemFromCar(ids,userId);
        ResponseData<ModifyItemResponse> responseData = new ResponseData<>();
        responseData.setMessage(resultData.getMessageEnum().getMessage())
                .setState(resultData.getMessageEnum().getState());
        if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return responseData.setData(new ModifyItemResponse(mallCarService.getCheckedItemAmount(userId),
                    mallCarService.isAllCheck(userId)));
        return responseData;

    }


    /**
     * 修改购物车数量
     */
    @PostMapping("/count")
    @ResponseBody
    public ResponseData<ModifyItemResponse> plushItemCount(@LoginUserId Integer userId,@RequestBody OrderItem orderItem){
        ResultData<Integer> resultData = mallCarService.modifyItemCount(orderItem);
        ResponseData<ModifyItemResponse> responseData = new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
        if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return responseData.setData(new ModifyItemResponse(mallCarService.getCheckedItemAmount(userId),
                    mallCarService.isAllCheck(userId)));
        return responseData;
    }


    /**
     * 勾选
     * @param userId
     * @param checkItems
     * @return
     */
    @PostMapping("/check")
    @ResponseBody
    public ResponseData<ModifyItemResponse> checkItem(@LoginUserId Integer userId,@RequestBody CheckItems checkItems){
        ResultData<Integer> resultData = mallCarService.checkItems(checkItems.getItems(),userId,checkItems.getCheck());
        ResponseData<ModifyItemResponse> responseData = new ResponseData<>(resultData.getMessageEnum().getMessage(),resultData.getMessageEnum().getState());
        if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return responseData.setData(new ModifyItemResponse(mallCarService.getCheckedItemAmount(userId),
                    mallCarService.isAllCheck(userId)
                    ));
        return responseData;
    }



}
