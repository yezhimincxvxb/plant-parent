package com.moguying.plant.core.controller.api;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.constant.MallEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.ValidateUser;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResponseData;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.common.vo.BuyResponse;
import com.moguying.plant.core.entity.mall.MallCompany;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.vo.*;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.seed.vo.SendPayOrderResponse;
import com.moguying.plant.core.entity.seed.vo.SubmitOrder;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.entity.user.vo.UserMallOrder;
import com.moguying.plant.core.service.mall.MallCompanyService;
import com.moguying.plant.core.service.mall.MallOrderDetailService;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.core.service.mall.MallProductService;
import com.moguying.plant.core.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mall")
@Slf4j
public class AMallController {

    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private MallOrderService mallOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MallOrderDetailService mallOrderDetailService;

    @Autowired
    private MallCompanyService mallCompanyService;

    @Value("${order.expire.time}")
    private int expireTime;

    /**
     * 商城订单列表
     *
     * @param orderSearch
     * @param userId
     * @return
     */
    @PostMapping("/order/search")
    public PageResult<UserMallOrder> orderList(@RequestBody OrderSearch orderSearch, @LoginUserId Integer userId) {

        if (null == orderSearch.getPage())
            orderSearch.setPage(1);
        if (null == orderSearch.getSize())
            orderSearch.setSize(10);
        PageResult<UserMallOrder> mallList = mallOrderService.userMallOrderListByState(orderSearch.getPage(), orderSearch.getSize(), userId,
                orderSearch.getState());
        mallOrderService.setUserMallOrderItemList(mallList.getData(), userId);
        return mallList;
    }


    /**
     * 商城订单详情
     */
    @GetMapping("/order/{id}")
    public ResponseData<OrderDetail> orderDetail(@PathVariable Integer id, @LoginUserId Integer userId) {
        MallOrder order = mallOrderService.selectOrderById(id);
        if (null == order)
            return new ResponseData<>(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage(), MessageEnum.MALL_ORDER_NOT_EXISTS.getState());

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getId());
        orderDetail.setState(order.getState());
        orderDetail.setStateStr(mallOrderService.getStateStr(order.getState()));
        UserAddress address = userService.userAddressByIdAndUserId(order.getAddressId(), userId, null);
        orderDetail.setAddress(address);
        orderDetail.setMark(order.getBuyMark());
        orderDetail.setOrderNumber(order.getOrderNumber());
        orderDetail.setAddTime(order.getAddTime());
        orderDetail.setPayTime(order.getPayTime());
        orderDetail.setSendTime(order.getSendTime());
        orderDetail.setConfirmTime(order.getConfirmTime());
        orderDetail.setCloseTime(order.getCloseTime());
        orderDetail.setProductAmount(order.getBuyAmount());
        orderDetail.setExpressFee(order.getFeeAmount());
        orderDetail.setTotalAmount(order.getBuyAmount().add(order.getFeeAmount()));
        orderDetail.setIsNotice(order.getIsNotice());
        orderDetail.setTotalCoins(order.getTotalCoins());
        if (order.getState().equals(MallEnum.ORDER_NEED_PAY.getState())) {
            long leftSecond = expireTime - (((new Date()).getTime() - order.getAddTime().getTime()) / 1000);
            orderDetail.setLeftSecond((int) leftSecond);
        }
        List<OrderItem> details = mallOrderDetailService.orderItemListByOrderIdAndUserId(order.getId(), userId);
        orderDetail.setOrderItems(details);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), orderDetail);
    }


    /**
     * 立即购买
     *
     * @param userId
     * @param orderBuy
     * @return
     */
    @ValidateUser
    @PostMapping("/buy")
    public ResponseData<OrderBuyResponse> orderBuy(@LoginUserId Integer userId, @RequestBody OrderBuy orderBuy) {
        if (null == orderBuy || null == orderBuy.getProducts() || orderBuy.getProducts().size() == 0)
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        ResultData<OrderBuyResponse> responseResultData = mallProductService.orderBuy(userId, orderBuy);
        return new ResponseData<>(responseResultData.getMessageEnum().getMessage(), responseResultData.getMessageEnum().getState(), responseResultData.getData());
    }


    /**
     * 计算订单总价
     *
     * @param submitOrder
     * @return
     */
    @ValidateUser
    @PostMapping("/sum")
    public ResponseData<OrderSum> sumOrder(@LoginUserId Integer userId, @RequestBody SubmitOrder submitOrder) {
        ResultData<OrderSum> resultData = mallProductService.sumOrder(userId, submitOrder);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 提交订单
     *
     * @param submitOrder
     * @param userId
     * @return
     */
    @PostMapping("/order")
    @ResponseBody
    public ResponseData<BuyResponse> submitOrder(@RequestBody SubmitOrder submitOrder, @LoginUserId Integer userId) {
        ResultData<BuyResponse> resultData = mallProductService.submitOrder(submitOrder, userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 提交订单信息至第三方支付
     *
     * @return
     */
    @PostMapping("/pay")
    public ResponseData<SendPayOrderResponse> checkPayOrder(@LoginUserId Integer userId, @RequestBody SendPayOrder payOrder) {
        if (null == payOrder.getIsCheck())
            return new ResponseData<>(MessageEnum.MALL_ORDER_PAY_TYPE_ERROR.getMessage(), MessageEnum.MALL_ORDER_PAY_TYPE_ERROR.getState());
        if (null == payOrder.getOrderId())
            return new ResponseData<>(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage(), MessageEnum.MALL_ORDER_NOT_EXISTS.getState());
        ResultData<SendPayOrderResponse> resultData = mallOrderService.checkPayOrder(payOrder, userId);
        if (resultData.getMessageEnum().equals(MessageEnum.ERROR))
            return new ResponseData<>(resultData.getData().getErrorMsg(), resultData.getMessageEnum().getState());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 通过第三方支付后发起支付
     *
     * @return
     */
    @PutMapping("/pay")
    @SuppressWarnings("all")
    public ResponseData<Integer> payOrder(@LoginUserId Integer userId, @RequestBody SendPayOrder payOrder) {
        if ((null != payOrder.getPayMsgCode() && null != payOrder.getPayPassword()) ||
                (null == payOrder.getPayPassword() && null == payOrder.getPayMsgCode()))
            return new ResponseData<>(MessageEnum.MALL_ORDER_PAY_TYPE_ERROR.getMessage(), MessageEnum.MALL_ORDER_PAY_TYPE_ERROR.getState());
        if (null != payOrder.getPayMsgCode() && null == payOrder.getSeqNo())
            return new ResponseData<>(MessageEnum.MESSAGE_SERIAL_NO_EMPTY.getMessage(), MessageEnum.MESSAGE_SERIAL_NO_EMPTY.getState());
        ResultData<PaymentResponse> resultData = mallOrderService.payOrder(payOrder, userId);
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), payOrder.getOrderId());
        } else if (null != resultData.getData()) {
            return new ResponseData<>(resultData.getData().getMsg(), MessageEnum.ERROR.getState());
        }
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 取消订单
     *
     * @param cancelOrder
     * @return
     */
    @PostMapping("/cancel")
    public ResponseData<Integer> cancelOrder(@RequestBody CancelOrder cancelOrder, @LoginUserId Integer userId) {
        MallOrder order = mallOrderService.selectOrderById(cancelOrder.getId());
        if (null == order)
            return new ResponseData<>(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage(), MessageEnum.MALL_ORDER_NOT_EXISTS.getState());

        if (StringUtils.isEmpty(cancelOrder.getCancelReason()))
            return new ResponseData<>(MessageEnum.MALL_CANCEL_REASON_EMPTY.getMessage(), MessageEnum.MALL_CANCEL_REASON_EMPTY.getState());

        ResultData<Integer> resultData = mallOrderService.cancelOrder(cancelOrder, userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 提醒发货/确认收货
     *
     * @return
     */
    @PutMapping("/order")
    public ResponseData<Integer> noticeOrder(@RequestBody NoticeOrder noticeOrder) {
        if (null == noticeOrder || null == noticeOrder.getOrderId())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        MallOrder order = mallOrderService.selectOrderById(noticeOrder.getOrderId());
        if (order == null)
            return new ResponseData<>(MessageEnum.MALL_ORDER_NOT_EXISTS.getMessage(), MessageEnum.MALL_ORDER_NOT_EXISTS.getState());

        MallOrder update = new MallOrder();
        update.setId(order.getId());
        if (noticeOrder.getState().equals(MallEnum.ORDER_HAS_DONE.getState())) {
            update.setState(noticeOrder.getState());
            update.setConfirmTime(new Date());
        } else if (noticeOrder.getState().equals(MallEnum.ORDER_HAS_PAY.getState())) {
            update.setIsNotice(true);
            update.setNoticeTime(new Date());
        }
        if (mallOrderService.saveOrder(update) > 0)
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState());
        return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
    }


    /**
     * 物流信息
     */
    @PostMapping("/trace/info")
    public ResponseData<TraceInfo> traceInfo(@RequestBody NoticeOrder noticeOrder) {

        if (null == noticeOrder || null == noticeOrder.getOrderId()) {
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        }

        MallOrder order = mallOrderService.selectOrderById(noticeOrder.getOrderId());
        if (null == order) {
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        }

        // 请求第三方接口所需参数
        String expressComCode = Optional.ofNullable(order.getExpressComCode()).orElse("");
        String expressOrderNumber = Optional.ofNullable(order.getExpressOrderNumber()).orElse("");
        String phone = Optional.ofNullable(order.getAddress()).map(UserAddress::getReceivePhone).orElse("");

        TraceInfoParam traceInfoParam = new TraceInfoParam()
                .setCom(expressComCode)
                .setNum(expressOrderNumber)
                .setPhone(phone);

        // 获取物流实时数据(JSON字符串形式)
        String synQueryData = mallOrderService.synQueryData(traceInfoParam);
        if (StringUtils.isBlank(synQueryData)) {
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }

        // JSON字符串转对象
        TraceData traceData = JSON.parseObject(synQueryData, TraceData.class);
        if (Objects.isNull(traceData)) {
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }

        // 获取公司名称及公司电话
        MallCompany mallCompany = mallCompanyService.findByCode(expressComCode);
        if (Objects.isNull(mallCompany)) {
            return new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState());
        }

        // 封装数据
        TraceInfo traceInfo = new TraceInfo();
        String companyName = Optional.ofNullable(mallCompany.getCompanyName()).orElse("");
        String companyPhone = Optional.ofNullable(mallCompany.getCompanyPhone()).orElse("");
        String traceNumber = Optional.ofNullable(traceData.getNu()).orElse("");
        String stateInfo = Optional.ofNullable(traceData.getState()).orElse("");

        traceInfo.setCompanyName(companyName);
        traceInfo.setCompanyPhone(companyPhone);
        traceInfo.setTraceNumber(traceNumber);
        traceInfo.setStateInfo(stateInfo);

        // 封装物流详情数据
        List<TraceInfoDetail> traceInfoDetails = new ArrayList<>();
        List<TraceDataInfo> traceDataInfos = Optional.ofNullable(traceData.getData()).orElse(new ArrayList<>());
        if (traceDataInfos.isEmpty()) {
            traceInfo.setTraceDetail(traceInfoDetails);
        } else {
            traceDataInfos.stream()
                    .filter(Objects::nonNull)
                    .sorted(TraceDataInfo::compareTo)
                    .forEach(traceDataInfo -> {
                        String context = Optional.ofNullable(traceDataInfo.getContext()).orElse("");
                        Date traceTime = Optional.ofNullable(traceDataInfo.getFtime()).orElse(new Date());
                        traceInfoDetails.add(new TraceInfoDetail(traceTime, context));
                    });
            traceInfo.setTraceDetail(traceInfoDetails);
        }
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), traceInfo);
    }

    /**
     * 退款
     */
    @PutMapping("/order/refund")
    public ResponseData<Integer> orderRefund(@RequestBody NoticeOrder noticeOrder, @LoginUserId Integer userId) {
        ResultData<Integer> resultData = mallOrderService.orderRefund(noticeOrder.getOrderId(), userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }
}

