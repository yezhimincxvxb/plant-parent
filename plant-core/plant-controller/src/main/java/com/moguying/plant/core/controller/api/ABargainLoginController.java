package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.ShareVo;
import com.moguying.plant.core.entity.common.vo.BuyResponse;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.seed.vo.SubmitOrder;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.core.service.bargain.BargainLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/login/bargain")
@Slf4j
@Api(tags = "砍价活动-登录")
public class ABargainLoginController {

    @Autowired
    private MallProductDAO mallProductDAO;

    @Autowired
    private BargainDetailService bargainDetailService;

    @Autowired
    private BargainLogService bargainLogService;

    @Autowired
    private UserAddressDAO userAddressDAO;

    /**
     * 点击免费拿
     */
    @GetMapping("/pick/up/{productId}")
    @ApiOperation("点击免费拿")
    public ResponseData<ShareVo> pickUp(@LoginUserId Integer userId, @PathVariable("productId") Integer productId) {

        ResponseData<ShareVo> responseData = new ResponseData<>();

        // 参数错误
        if (Objects.isNull(userId) || Objects.isNull(productId))
            return responseData
                    .setMessage(MessageEnum.PARAMETER_ERROR.getMessage())
                    .setState(MessageEnum.PARAMETER_ERROR.getState());

        // 产品存在
        MallProduct product = mallProductDAO.selectById(productId);
        if (product == null || !product.getIsShow() || product.getBargainCount() <= 0 || product.getBargainNumber() <= 0)
            return responseData
                    .setMessage(MessageEnum.MALL_PRODUCT_NOT_EXISTS.getMessage())
                    .setState(MessageEnum.MALL_PRODUCT_NOT_EXISTS.getState());

        // 库存足够
        if (product.getLeftCount() < product.getBargainNumber())
            return responseData
                    .setMessage(MessageEnum.MALL_PRODUCT_NOT_ENOUGH.getMessage())
                    .setState(MessageEnum.MALL_PRODUCT_NOT_ENOUGH.getState());

        // 限量商品
        if (product.getIsLimit() && product.getBargainLimit() < product.getBargainNumber())
            return responseData
                    .setMessage(MessageEnum.MAX_LIMIT.getMessage())
                    .setState(MessageEnum.MAX_LIMIT.getState());

        // 首次分享
        ResultData<ShareVo> resultData = bargainDetailService.shareSuccess(userId, product);
        if (Objects.nonNull(resultData.getData())) responseData.setData(resultData.getData());

        return responseData
                .setMessage(resultData.getMessageEnum().getMessage())
                .setState(resultData.getMessageEnum().getState());
    }

    /**
     * 分享
     */
    @GetMapping("/share/{orderId}")
    @ApiOperation("分享")
    public ResponseData<ShareVo> share(@LoginUserId Integer userId, @PathVariable("orderId") Integer orderId) {

        ResponseData<ShareVo> responseData = new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());

        // 参数错误
        if (Objects.isNull(userId) || Objects.isNull(orderId))
            return responseData
                    .setMessage(MessageEnum.PARAMETER_ERROR.getMessage())
                    .setState(MessageEnum.PARAMETER_ERROR.getState());

        // 订单详情不存在
        BargainDetail detail = bargainDetailService.getOneById(orderId);
        if (Objects.isNull(detail))
            return responseData
                    .setMessage(MessageEnum.SHARE_NOT_FOUND.getMessage())
                    .setState(MessageEnum.SHARE_NOT_FOUND.getState());

        ShareVo shareVo = new ShareVo()
                .setUserId(userId)
                .setOrderId(orderId)
                .setSymbol(detail.getSymbol());

        return responseData.setData(shareVo);
    }

    /**
     * 砍价中的产品列表
     */
    @PostMapping("/doing/list")
    @ApiOperation("砍价中的产品列表")
    public PageResult<BargainVo> doingList(@LoginUserId Integer userId, @RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.doingList(pageSearch.getPage(), pageSearch.getSize(), userId);
    }

    /**
     * 已砍成功的产品列表
     */
    @PostMapping("/success/list")
    @ApiOperation("已砍成功的产品列表")
    public PageResult<BargainVo> successList(@LoginUserId Integer userId, @RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.successList(pageSearch.getPage(), pageSearch.getSize(), userId);
    }

    /**
     * 砍价
     */
    @PostMapping("/help/chop")
    @ApiOperation("砍价")
    public ResponseData<BargainVo> helpChop(@LoginUserId Integer userId, @RequestBody BargainDetail bargainDetail) {
        ResponseData<BargainVo> responseData = new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());

        // 参数
        if (bargainDetail == null || bargainDetail.getId() == null || bargainDetail.getUserId() == null)
            return responseData
                    .setMessage(MessageEnum.PARAMETER_ERROR.getMessage())
                    .setState(MessageEnum.PARAMETER_ERROR.getState());

        // 自己已砍
        if (userId.equals(bargainDetail.getUserId()))
            return responseData
                    .setMessage(MessageEnum.NOT_OWN_BARGAIN.getMessage())
                    .setState(MessageEnum.NOT_OWN_BARGAIN.getState());

        // 好友已帮
        Integer count = bargainLogService.getBargainCount(userId, bargainDetail.getId());
        if (count >= 1)
            return responseData
                    .setMessage(MessageEnum.HELPED_ORDER.getMessage())
                    .setState(MessageEnum.HELPED_ORDER.getState());

        // 当天帮砍上限
        Integer countToday = bargainLogService.getBargainCountToday(userId);
        if (countToday > 3)
            return responseData
                    .setMessage(MessageEnum.HELPED_OVER.getMessage())
                    .setState(MessageEnum.HELPED_OVER.getState());

        // 砍价详情
        BargainDetail detail = bargainDetailService.getOneById(bargainDetail.getId());
        if (detail == null || detail.getState())
            return responseData
                    .setMessage(MessageEnum.SHARE_NOT_FOUND.getMessage())
                    .setState(MessageEnum.SHARE_NOT_FOUND.getState());

        // 砍价口令
        if (!Objects.equals(bargainDetail.getSymbol(), detail.getSymbol()))
            return responseData
                    .setMessage(MessageEnum.BARGAIN_SYMBOL_ERROR.getMessage())
                    .setState(MessageEnum.BARGAIN_SYMBOL_ERROR.getState());

        // 帮砍
        detail.setMessage(bargainDetail.getMessage());
        BargainLog log = bargainDetailService.helpSuccess(userId, detail);
        if (log != null) {
            BargainVo bargainVo = new BargainVo();
            bargainVo.setHelpAmount(log.getHelpAmount());
            bargainVo.setMessage("砍价成功");
            return responseData.setData(bargainVo);
        }

        return responseData
                .setMessage(MessageEnum.ERROR.getMessage())
                .setState(MessageEnum.ERROR.getState());
    }

    /**
     * 好友帮砍记录
     */
    @PostMapping("/help/log")
    @ApiOperation("好友帮砍记录")
    public PageResult<BargainVo> helpLog(@LoginUserId Integer userId, @RequestBody PageSearch<Integer> pageSearch) {
        return bargainLogService.helpLog(pageSearch.getPage(), pageSearch.getSize(), userId, pageSearch.getWhere());
    }

    /**
     * 砍价成功记录(用户本人)
     */
    @PostMapping("/own/log")
    @ApiOperation("砍价成功记录(用户本人)")
    public PageResult<BargainVo> ownLog(@LoginUserId Integer userId, @RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.ownLog(pageSearch.getPage(), pageSearch.getSize(), userId);
    }

    /**
     * 提交订单
     */
    @PostMapping("/submit/order")
    @ApiOperation("提交订单")
    public ResponseData<BuyResponse> submitOrder(@LoginUserId Integer userId, @RequestBody SubmitOrder submitOrder) {

        ResponseData<BuyResponse> responseData = new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), null);

        // 参数
        if (submitOrder == null || submitOrder.getProductId() == null || submitOrder.getAddressId() == null)
            return responseData
                    .setMessage(MessageEnum.PARAMETER_ERROR.getMessage())
                    .setState(MessageEnum.PARAMETER_ERROR.getState());

        // 商品不存在
        MallProduct product = mallProductDAO.selectById(submitOrder.getProductId());
        if (product == null || !product.getIsShow())
            return responseData
                    .setMessage(MessageEnum.MALL_PRODUCT_NOT_EXISTS.getMessage())
                    .setState(MessageEnum.MALL_PRODUCT_NOT_EXISTS.getState());

        // 地址不存在
        UserAddress address = userAddressDAO.selectByIdAndUserId(submitOrder.getAddressId(), userId, false);
        if (null == address)
            return responseData
                    .setMessage(MessageEnum.USER_ADDRESS_NO_EXISTS.getMessage())
                    .setState(MessageEnum.USER_ADDRESS_NO_EXISTS.getState());

        // 砍价详情
        BargainDetail detail = bargainDetailService.getOneByClose(userId, submitOrder.getProductId(), true);
        if (detail == null)
            return responseData
                    .setMessage(MessageEnum.SHARE_NOT_FOUND.getMessage())
                    .setState(MessageEnum.SHARE_NOT_FOUND.getState());

        // 订单存在
        MallOrder mallOrder = bargainDetailService.submitOrder(userId, submitOrder.getProductId());
        if (mallOrder == null)
            return responseData
                    .setMessage(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage())
                    .setState(MessageEnum.MALL_ORDER_NOT_EXISTS.getState());

        // 提单
        mallOrder.setBuyMark(submitOrder.getMark());
        Boolean submitSuccess = bargainDetailService.submitSuccess(userId, product, address, detail, mallOrder);
        if (submitSuccess) {
            BuyResponse buyResponse = new BuyResponse();
            buyResponse.setOrderId(mallOrder.getId());
            buyResponse.setOrderNumber(mallOrder.getOrderNumber());
            buyResponse.setAddress(address);
            return responseData.setData(buyResponse);
        }

        return responseData
                .setMessage(MessageEnum.MALL_ORDER_UPDATE_ERROR.getMessage())
                .setState(MessageEnum.MALL_ORDER_UPDATE_ERROR.getState());
    }


}
