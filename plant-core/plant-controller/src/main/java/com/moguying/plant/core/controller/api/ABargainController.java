package com.moguying.plant.core.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.NoLogin;
import com.moguying.plant.core.dao.bargain.BargainDetailDao;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.bargain.BargainDetail;
import com.moguying.plant.core.entity.bargain.BargainLog;
import com.moguying.plant.core.entity.bargain.BargainRate;
import com.moguying.plant.core.entity.bargain.vo.BargainVo;
import com.moguying.plant.core.entity.bargain.vo.SendNumberVo;
import com.moguying.plant.core.entity.bargain.vo.ShareVo;
import com.moguying.plant.core.entity.common.vo.BuyResponse;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.MallProduct;
import com.moguying.plant.core.entity.seed.vo.SubmitOrder;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.core.service.bargain.BargainLogService;
import com.moguying.plant.core.service.mall.MallProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/bargain")
@Slf4j
@Api(tags = "砍价活动")
public class ABargainController {

    @Autowired
    private MallProductDAO mallProductDAO;

    @Autowired
    private BargainDetailService bargainDetailService;

    @Autowired
    private BargainLogService bargainLogService;

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Autowired
    private BargainDetailDao bargainDetailDao;

    @Autowired
    private MallProductService mallProductService;

    /**
     * 砍价产品列表
     */
    @NoLogin
    @PostMapping("/product/list")
    @ApiOperation("砍价产品列表")
    public PageResult<BargainVo> productList(@RequestBody PageSearch pageSearch) {
        PageResult<BargainVo> pageResult = mallProductService.productList(pageSearch.getPage(), pageSearch.getSize());
        List<BargainVo> bargainList = pageResult.getData();
        if (bargainList == null || bargainList.isEmpty()) return pageResult.setData(new ArrayList<>());
        // 移除限量商品数量不足
        bargainList.removeIf(bargain -> bargain.getIsLimit() && bargain.getTotalNumber() < bargain.getProductCount());
        // 没有送出数量
        List<SendNumberVo> sendNumbers = bargainDetailService.sendNumber();
        if (sendNumbers == null || sendNumbers.isEmpty()) {
            bargainList.forEach(bargain -> {
                bargain.setSendNumber(0);
                if (bargain.getIsLimit()) bargain.setLeftNumber(bargain.getTotalNumber());
            });
            return pageResult.setData(bargainList);
        }
        // 设置送出数量
        bargainList.forEach(bargain -> {
            Integer productCount = sendNumbers.stream()
                    .filter(Objects::nonNull)
                    .filter(sendNumber -> bargain.getProductId().equals(sendNumber.getProductId()))
                    .map(SendNumberVo::getProductCount)
                    .findFirst()
                    .orElse(0);
            bargain.setSendNumber(productCount);
        });
        // 剩余数量
        bargainList.stream()
                .filter(Objects::nonNull)
                .filter(BargainVo::getIsLimit)
                .forEach(bargain -> {
                    bargain.setLeftNumber(bargain.getTotalNumber() - bargain.getSendNumber());
                });
        return pageResult.setData(bargainList);
    }

    /**
     * 点击免费拿
     */
    @GetMapping("/pick/up/{productId}")
    @ApiOperation("点击免费拿")
    public ResponseData<ShareVo> pickUp(@LoginUserId Integer userId, @PathVariable("productId") Integer productId) {
        // 参数错误
        if (Objects.isNull(userId) || Objects.isNull(productId))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        // 产品存在
        MallProduct product = mallProductDAO.selectById(productId);
        if (product == null || !product.getIsShow())
            return new ResponseData<>(MessageEnum.MALL_PRODUCT_NOT_EXISTS.getMessage(), MessageEnum.MALL_PRODUCT_NOT_EXISTS.getState());
        // 砍价参数
        BargainRate bargainRate = bargainDetailService.getBargainRate(product.getId());
        if (bargainRate == null || bargainRate.getBargainCount() <= 0 || bargainRate.getBargainNumber() <= 0)
            return new ResponseData<>(MessageEnum.NOT_BARGAIN_RATE.getMessage(), MessageEnum.NOT_BARGAIN_RATE.getState());
        // 库存足够
        if (product.getLeftCount() < bargainRate.getBargainNumber())
            return new ResponseData<>(MessageEnum.MALL_PRODUCT_NOT_ENOUGH.getMessage(), MessageEnum.MALL_PRODUCT_NOT_ENOUGH.getState());
        // 限量商品
        if (bargainRate.getIsLimit() && bargainRate.getBargainLimit() < bargainRate.getBargainNumber())
            return new ResponseData<>(MessageEnum.MAX_LIMIT.getMessage(), MessageEnum.MAX_LIMIT.getState());
        // 首次分享
        ResultData<ShareVo> resultData = bargainDetailService.shareSuccess(userId, product, bargainRate);
        if (Objects.nonNull(resultData.getData()))
            return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }

    /**
     * 分享
     */
    @GetMapping("/share/{orderId}")
    @ApiOperation("分享")
    public ResponseData<ShareVo> share(@LoginUserId Integer userId, @PathVariable("orderId") Integer orderId) {
        if (Objects.isNull(userId) || Objects.isNull(orderId))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        BargainDetail detail = bargainDetailService.getOneById(orderId);
        if (Objects.isNull(detail))
            return new ResponseData<>(MessageEnum.SHARE_NOT_FOUND.getMessage(), MessageEnum.SHARE_NOT_FOUND.getState());
        ShareVo shareVo = new ShareVo()
                .setUserId(userId)
                .setOrderId(orderId)
                .setSymbol(detail.getSymbol());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), shareVo);
    }

    /**
     * 砍价中的产品列表
     */
    @PostMapping("/doing/list")
    @ApiOperation("砍价中的产品列表")
    public PageResult<BargainVo> doingList(@LoginUserId Integer userId, @RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.doingList(pageSearch.getPage(), pageSearch.getSize(), userId, false);
    }

    /**
     * 砍价中的产品详情
     */
    @NoLogin
    @PostMapping("/product/one")
    @ApiOperation("砍价中的产品详情")
    public ResponseData<BargainVo> productInfoByOrderId(@RequestBody BargainVo bargainVo) {
        if (Objects.isNull(bargainVo) || (Objects.isNull(bargainVo.getOrderId()) && Objects.isNull(bargainVo.getSymbol())))
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), new BargainVo());
        BargainVo response = bargainDetailService.productInfoByOrderId(bargainVo);
        if (Objects.isNull(response))
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), new BargainVo());
        Integer productId = response.getProductId();
        Integer number = bargainDetailService.getNumber(productId);
        response.setSendNumber(number);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), response);
    }

    /**
     * 是否帮过
     */
    @PostMapping("/is/help")
    @ApiOperation("是否帮过")
    public ResponseData<ResponseData> isHelp(@LoginUserId Integer userId, @RequestBody BargainVo bargainVo) {
        if (Objects.isNull(bargainVo) || Objects.isNull(bargainVo.getSymbol()))
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        LambdaQueryWrapper<BargainDetail> queryWrapper = new QueryWrapper<BargainDetail>()
                .lambda().eq(BargainDetail::getSymbol, bargainVo.getSymbol());
        BargainDetail detail = bargainDetailDao.selectOne(queryWrapper);
        if (Objects.isNull(detail))
            return new ResponseData<>(MessageEnum.NOT_BARGAIN_DETAIL.getMessage(), MessageEnum.NOT_BARGAIN_DETAIL.getState());
        if (userId.equals(detail.getUserId()) || detail.getState())
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), new ResponseData().setState(0));
        Integer count = bargainLogService.getBargainCount(userId, detail.getId());
        if (count >= 1)
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), new ResponseData().setState(0));
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), new ResponseData().setState(1));
    }

    /**
     * 砍价
     */
    @PostMapping("/help/chop")
    @ApiOperation("砍价")
    public ResponseData<BargainVo> helpChop(@LoginUserId Integer userId, @RequestBody BargainDetail bargainDetail) {
        if (bargainDetail == null || bargainDetail.getId() == null || bargainDetail.getUserId() == null)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        // 自己已砍
        if (userId.equals(bargainDetail.getUserId()))
            return new ResponseData<>(MessageEnum.NOT_OWN_BARGAIN.getMessage(), MessageEnum.NOT_OWN_BARGAIN.getState());
        // 好友已帮
        Integer count = bargainLogService.getBargainCount(userId, bargainDetail.getId());
        if (count >= 1)
            return new ResponseData<>(MessageEnum.HELPED_ORDER.getMessage(), MessageEnum.HELPED_ORDER.getState());
        // 当天帮砍上限
        Integer countToday = bargainLogService.getBargainCountToday(userId);
        if (countToday > 3)
            return new ResponseData<>(MessageEnum.HELPED_OVER.getMessage(), MessageEnum.HELPED_OVER.getState());
        // 砍价详情
        BargainDetail detail = bargainDetailService.getOneById(bargainDetail.getId());
        if (detail == null || detail.getState())
            return new ResponseData<>(MessageEnum.SHARE_NOT_FOUND.getMessage(), MessageEnum.SHARE_NOT_FOUND.getState());
        // 砍价口令
        if (StringUtils.isNotEmpty(bargainDetail.getSymbol()) && !bargainDetail.getSymbol().equals(detail.getSymbol()))
            return new ResponseData<>(MessageEnum.BARGAIN_SYMBOL_ERROR.getMessage(), MessageEnum.BARGAIN_SYMBOL_ERROR.getState());
        // 帮砍
        detail.setMessage(bargainDetail.getMessage());
        BargainLog log = bargainDetailService.helpSuccess(userId, detail);
        if (log != null) {
            BargainVo bargainVo = new BargainVo();
            bargainVo.setHelpAmount(log.getHelpAmount());
            bargainVo.setMessage("砍价成功");
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), bargainVo);
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
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
     * 已砍成功但未提交
     */
    @PostMapping("/success/list")
    @ApiOperation("已砍成功但未提交")
    public PageResult<BargainVo> successList(@LoginUserId Integer userId, @RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.doingList(pageSearch.getPage(), pageSearch.getSize(), userId, true);
    }

    /**
     * 提交订单
     */
    @PostMapping("/submit/order")
    @ApiOperation("提交订单")
    public ResponseData<BuyResponse> submitOrder(@LoginUserId Integer userId, @RequestBody SubmitOrder submitOrder) {
        if (submitOrder == null || submitOrder.getProductId() == null || submitOrder.getAddressId() == null)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        // 商品不存在
        MallProduct product = mallProductDAO.selectById(submitOrder.getProductId());
        if (product == null || !product.getIsShow())
            return new ResponseData<>(MessageEnum.MALL_PRODUCT_NOT_EXISTS.getMessage(), MessageEnum.MALL_PRODUCT_NOT_EXISTS.getState());
        // 地址不存在
        UserAddress address = userAddressDAO.selectByIdAndUserId(submitOrder.getAddressId(), userId, false);
        if (null == address)
            return new ResponseData<>(MessageEnum.USER_ADDRESS_NO_EXISTS.getMessage(), MessageEnum.USER_ADDRESS_NO_EXISTS.getState());
        // 砍价详情
        BargainDetail detail = bargainDetailService.getOneByClose(userId, submitOrder.getProductId(), true);
        if (detail == null)
            return new ResponseData<>(MessageEnum.SHARE_NOT_FOUND.getMessage(), MessageEnum.SHARE_NOT_FOUND.getState());
        // 订单存在
        MallOrder mallOrder = bargainDetailService.submitOrder(userId, submitOrder.getProductId());
        if (mallOrder == null)
            return new ResponseData<>(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage(), MessageEnum.MALL_ORDER_NOT_EXISTS.getState());
        // 提单
        mallOrder.setBuyMark(submitOrder.getMark());
        Boolean submitSuccess = bargainDetailService.submitSuccess(userId, product, address, detail, mallOrder);
        if (submitSuccess) {
            BuyResponse buyResponse = new BuyResponse();
            buyResponse.setOrderId(mallOrder.getId());
            buyResponse.setOrderNumber(mallOrder.getOrderNumber());
            buyResponse.setAddress(address);
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), buyResponse);
        }
        return new ResponseData<>(MessageEnum.MALL_ORDER_UPDATE_ERROR.getMessage(), MessageEnum.MALL_ORDER_UPDATE_ERROR.getState());
    }

    /**
     * 砍价成功记录(所有用户)
     */
    @NoLogin
    @PostMapping("/success/logs")
    @ApiOperation("砍价成功记录(所有用户)")
    public PageResult<BargainVo> successLogs(@RequestBody PageSearch<?> pageSearch) {
        return bargainDetailService.successLogs(pageSearch.getPage(), pageSearch.getSize());
    }


}
