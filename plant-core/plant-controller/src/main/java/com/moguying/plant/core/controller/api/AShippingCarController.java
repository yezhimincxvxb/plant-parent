package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.common.vo.ModifyItemResponse;
import com.moguying.plant.core.entity.mall.MallCar;
import com.moguying.plant.core.entity.mall.vo.BuyProduct;
import com.moguying.plant.core.entity.mall.vo.CarItemList;
import com.moguying.plant.core.entity.mall.vo.CheckItems;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.core.service.mall.MallCarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mall/car")
@Api(tags = "商城购物车")
public class AShippingCarController {


    @Autowired
    private MallCarService mallCarService;


    /**
     * 添加入购物车
     */
    @PostMapping
    @ApiOperation("添加入购物车")
    public ResponseData<Integer> addToCar(@RequestBody BuyProduct itemToCar, @LoginUserId Integer userId) {
        MallCar mallCar = new MallCar();
        mallCar.setUserId(userId);
        mallCar.setProductCount(itemToCar.getBuyCount());
        mallCar.setProductId(itemToCar.getProductId());
        ResultData<Integer> resultData = mallCarService.addItemToCar(mallCar);
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 购物车列表
     */
    @PostMapping("/list")
    @ApiOperation("购物车列表")
    public ResponseData<CarItemList> carItems(@LoginUserId Integer userId, @RequestBody PageSearch search) {
        CarItemList carItemList = new CarItemList();
        PageResult<OrderItem> pageResult = mallCarService.userCarItems(search.getPage(), search.getSize(), userId);
        carItemList.setCheckedAmount(mallCarService.getCheckedItemAmount(userId));
        carItemList.setItems(pageResult.getData());
        carItemList.setCount(pageResult.getCount());
        carItemList.setCheckAll(mallCarService.isAllCheck(userId));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), carItemList);
    }


    /**
     * 删除购物车中的商品
     */
    @PostMapping("/delete")
    @ApiOperation("删除购物车中的商品")
    public ResponseData<ModifyItemResponse> deleteItems(@LoginUserId Integer userId, @RequestBody List<OrderItem> ids) {
        ResultData<Boolean> resultData = mallCarService.removeItemFromCar(ids, userId);
        ResponseData<ModifyItemResponse> responseData = new ResponseData<>();
        responseData.setMessage(resultData.getMessageEnum().getMessage())
                .setState(resultData.getMessageEnum().getState());
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return responseData.setData(new ModifyItemResponse(mallCarService.getCheckedItemAmount(userId),
                    mallCarService.isAllCheck(userId)));
        return responseData;

    }


    /**
     * 修改购物车数量
     */
    @PostMapping("/count")
    @ApiOperation("修改购物车数量")
    public ResponseData<ModifyItemResponse> plushItemCount(@LoginUserId Integer userId, @RequestBody OrderItem orderItem) {
        ResultData<Integer> resultData = mallCarService.modifyItemCount(orderItem);
        ResponseData<ModifyItemResponse> responseData = new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return responseData.setData(new ModifyItemResponse(mallCarService.getCheckedItemAmount(userId),
                    mallCarService.isAllCheck(userId)));
        return responseData;
    }


    /**
     * 勾选
     *
     * @param userId
     * @param checkItems
     * @return
     */
    @PostMapping("/check")
    @ApiOperation("勾选")
    public ResponseData<ModifyItemResponse> checkItem(@LoginUserId Integer userId, @RequestBody CheckItems checkItems) {
        ResultData<Integer> resultData = mallCarService.checkItems(checkItems.getItems(), userId, checkItems.getCheck());
        ResponseData<ModifyItemResponse> responseData = new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS))
            return responseData.setData(new ModifyItemResponse(mallCarService.getCheckedItemAmount(userId),
                    mallCarService.isAllCheck(userId)
            ));
        return responseData;
    }


}
