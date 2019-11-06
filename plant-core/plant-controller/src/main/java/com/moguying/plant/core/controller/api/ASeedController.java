package com.moguying.plant.core.controller.api;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.SeedEnum;
import com.moguying.plant.core.annotation.LoginUserId;
import com.moguying.plant.core.annotation.ValidateUser;
import com.moguying.plant.core.dao.reap.SaleCoinDao;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.entity.*;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.block.Block;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.UserSaleCoin;
import com.moguying.plant.core.entity.coin.vo.ExcReap;
import com.moguying.plant.core.entity.coin.vo.ExchangeInfo;
import com.moguying.plant.core.entity.fertilizer.Fertilizer;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.vo.PayOrderResponse;
import com.moguying.plant.core.entity.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.payment.request.WebHtmlPayRequest;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.reap.Reap;
import com.moguying.plant.core.entity.reap.vo.SaleRequest;
import com.moguying.plant.core.entity.reap.vo.SaleResponse;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.*;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.UserAddress;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.block.BlockService;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.core.service.mall.MallProductService;
import com.moguying.plant.core.service.order.PlantOrderService;
import com.moguying.plant.core.service.reap.ReapService;
import com.moguying.plant.core.service.reap.SaleCoinService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/seed")
@Slf4j
public class ASeedController {


    @Autowired
    private PlantOrderService plantOrderService;

    @Autowired
    private SeedOrderDetailService seedOrderDetailService;

    @Autowired
    private ReapService reapService;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private BlockService blockService;

    @Value("${order.expire.time}")
    private int expireTime;

    @Autowired
    private MallProductService mallProductService;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private SaleCoinService saleCoinService;

    @Autowired
    private SaleCoinDao saleCoinDao;

    @Autowired
    private MallOrderService mallOrderService;

    @Autowired
    private UserAddressDAO userAddressDAO;

    /**
     * 提交菌包订单
     *
     * @param buyOrder
     * @return
     */
    @ValidateUser
    @PostMapping(value = "/buy")
    public ResponseData<BuyOrderResponse> buySeed(@LoginUserId Integer userId, @RequestBody BuyOrder buyOrder) {
        if (null == buyOrder.getCount() || buyOrder.getCount() <= 0)
            return new ResponseData<>(MessageEnum.SEED_COUNT_NOT_RIGHT.getMessage(), MessageEnum.SEED_COUNT_NOT_RIGHT.getState());

        if (null == buyOrder.getSeedId() || buyOrder.getSeedId() <= 0)
            return new ResponseData<>(MessageEnum.SEED_NOT_EXISTS.getMessage(), MessageEnum.SEED_NOT_EXISTS.getState());

        ResultData<BuyOrderResponse> resultData = plantOrderService.plantOrder(buyOrder, userId);

        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 提交菌包支付订单
     *
     * @return
     */
    @ValidateUser
    @PostMapping("/pay")
    public ResponseData<SendPayOrderResponse> sendPayOrder(@LoginUserId Integer userId, @RequestBody SendPayOrder payOrder) {
        if (null == payOrder.getIsCheck())
            return new ResponseData<>(MessageEnum.SEED_ORDER_PAY_TYPE_ERROR.getMessage(), MessageEnum.SEED_ORDER_PAY_TYPE_ERROR.getState());

        if (null == payOrder.getOrderId())
            return new ResponseData<>(MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getMessage(), MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getState());

        ResultData<SendPayOrderResponse> resultData = plantOrderService.checkPayOrder(payOrder, userId);
        if (resultData.getMessageEnum().equals(MessageEnum.ERROR))
            return new ResponseData<>(resultData.getData().getErrorMsg(), resultData.getMessageEnum().getState());
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 支付订单
     *
     * @param payOrder
     * @param userId
     * @return
     */
    @ValidateUser
    @PutMapping("/pay")
    public ResponseData<PayOrderResponse> payOrder(@LoginUserId Integer userId, @RequestBody SendPayOrder payOrder) {
        if ((null != payOrder.getPayMsgCode() && null != payOrder.getPayPassword()) ||
                (null == payOrder.getPayPassword() && null == payOrder.getPayMsgCode()))
            return new ResponseData<>(MessageEnum.SEED_ORDER_PAY_TYPE_ERROR.getMessage(), MessageEnum.SEED_ORDER_PAY_TYPE_ERROR.getState());

        if (null != payOrder.getPayMsgCode() && null == payOrder.getSeqNo())
            return new ResponseData<>(MessageEnum.MESSAGE_SERIAL_NO_EMPTY.getMessage(), MessageEnum.MESSAGE_SERIAL_NO_EMPTY.getState());

        ResultData<PaymentResponse> resultData = plantOrderService.payOrder(payOrder, userId);
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            SeedOrderDetail seedOrderDetail = seedOrderDetailService.orderDetailByIdAndUserId(payOrder.getOrderId(), userId);
            Block block = blockService.findBlockBySeedType(seedOrderDetail.getSeedTypeId());
            PayOrderResponse response = new PayOrderResponse();
            response.setBlockId(block.getId());
            return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), response);
        } else if (null != resultData.getData()) {
            return new ResponseData<>(resultData.getData().getMsg(), MessageEnum.ERROR.getState());
        }
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
    }


    /**
     * 订单付款
     *
     * @param userId
     * @param payOrder
     * @return
     */
    @ValidateUser
    @PostMapping("/order/pay")
    public ResponseData<BuyOrderResponse> orderPay(@LoginUserId Integer userId, @RequestBody SendPayOrder payOrder) {
        if (null == payOrder.getOrderId())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());

        SeedOrderDetail detail = seedOrderDetailService.orderDetailByIdAndUserId(payOrder.getOrderId(), userId);
        if (null == detail)
            return new ResponseData<>(MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getMessage(), MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getState());

        if (detail.getState().equals(SeedEnum.SEED_ORDER_DETAIL_HAS_CLOSE.getState()) ||
                detail.getState().equals(SeedEnum.SEED_ORDER_DETAIL_HAS_PAY.getState()))
            return new ResponseData<>(MessageEnum.SEED_ORDER_DETAIL_HAS_PAY.getMessage(), MessageEnum.SEED_ORDER_DETAIL_HAS_PAY.getState());

        BuyOrderResponse buyOrderResponse = new BuyOrderResponse();
        buyOrderResponse.setOrderId(detail.getId());
        buyOrderResponse.setPerPrice(detail.getSeedPrice());
        buyOrderResponse.setGrowDays(detail.getSeedGrowDays());
        buyOrderResponse.setBuyCount(detail.getBuyCount());
        buyOrderResponse.setBuyAmount(detail.getBuyAmount());
        buyOrderResponse.setSeedName(detail.getSeedName());
        buyOrderResponse.setOrderNumber(detail.getOrderNumber());
        UserMoney userMoney = userMoneyService.userMoneyInfo(detail.getUserId());
        buyOrderResponse.setAvailableMoney(userMoney.getAvailableMoney());
        long left = (expireTime * 1000) - (new Date().getTime() - detail.getAddTime().getTime());
        if (left > 0)
            buyOrderResponse.setLeftSecond((int) (left / 1000));
        else
            buyOrderResponse.setLeftSecond(0);
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), buyOrderResponse);
    }


    /**
     * 取消订单
     *
     * @param userId
     * @param payOrder
     * @return
     */
    @ValidateUser
    @PostMapping("/order/cancel")
    public ResponseData<Integer> orderCancel(@LoginUserId Integer userId, @RequestBody SendPayOrder payOrder) {
        if (null == payOrder.getOrderId())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        ResultData<Integer> resultData = seedOrderDetailService.seedOrderCancel(payOrder.getOrderId(), userId);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 用户种植菌包
     *
     * @return
     */
    @ValidateUser
    @PostMapping(value = "/plant")
    public ResponseData<PlantOrderResponse> plantSeed(@LoginUserId Integer userId, @RequestBody PlantOrder plantOrder) {
        ResultData<TriggerEventResult<PlantOrderResponse>> resultData = plantOrderService.plantSeed(userId, plantOrder);
        ResponseData<PlantOrderResponse> responseData = new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState());
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            return responseData.setData(resultData.getData().getData());
        }
        return responseData;
    }


    /**
     * 采摘兑换实物
     *
     * @param reapId
     * @param excReap
     * @return
     */
    @PostMapping(value = "/exc/{reapId}")
    public ResponseData<Integer> exchangeReap(@PathVariable Integer reapId,
                                              @RequestBody ExcReap excReap) {
        ResultData<Integer> resultData = plantOrderService.plantReapExchange(reapId, excReap);
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), resultData.getData());
    }


    /**
     * 出售产品
     *
     * @param saleRequest
     * @return
     */
    @ValidateUser
    @PostMapping(value = "/sale")
    public ResponseData<SaleResponse> saleSeed(@LoginUserId Integer userId, @RequestBody SaleRequest saleRequest) {
        if (null == saleRequest.getSeedType())
            return new ResponseData<>(MessageEnum.PARAMETER_ERROR.getMessage(), MessageEnum.PARAMETER_ERROR.getState());
        ResultData<TriggerEventResult<InnerMessage>> resultData = reapService.saleReap(saleRequest.getSeedType(), userId);
        SaleResponse saleResponse = new SaleResponse();
        if (null != resultData.getData())
            saleResponse.setSaleAmount(new BigDecimal(resultData.getData().getData().getAmount()));
        return new ResponseData<>(resultData.getMessageEnum().getMessage(), resultData.getMessageEnum().getState(), saleResponse);
    }


    /**
     * PC端支付参数
     *
     * @param orderId
     * @return
     */
    @ValidateUser
    @GetMapping("/pay/data/{orderId}")
    public ResponseData<PaymentRequest<WebHtmlPayRequest>> payData(@LoginUserId Integer userId, @PathVariable Integer orderId) {
        SeedOrderDetail orderDetail = seedOrderDetailService.orderDetailByIdAndUserId(orderId, userId);
        if (null == orderDetail || !orderDetail.getState().equals(SeedEnum.SEED_ORDER_DETAIL_NEED_PAY.getState()))
            return new ResponseData<>(MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getMessage(), MessageEnum.SEED_ORDER_DETAIL_NOT_EXISTS.getState());
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(),
                plantOrderService.webHtmlPayRequestData(orderDetail));
    }

    /**
     * 兑换列表 (需要用户登录)
     */
    @PostMapping("/exchangeListByUser")
    public PageResult<ExchangeInfo> exchangeListByUser(@LoginUserId Integer userId, @RequestBody PageSearch<Integer> pageSearch) {
        Integer page = pageSearch.getPage();
        Integer size = pageSearch.getSize();
        // 根据where，显示不同的列表
        return reapService.showReap(page, size, userId);
    }

    /**
     * 兑换蘑菇币
     */
    @PostMapping("/exchangeMGB")
    public ResponseData<String> exchangeMGB(@LoginUserId Integer userId, @RequestBody PageSearch<String> pageSearch) {

        ResponseData<String> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), "兑换失败");

        // 参数错误
        String productName = pageSearch.getWhere();
        if (StringUtils.isEmpty(productName)) return responseData;

        // 用户不存在
        UserMoney userMoney = userMoneyService.userMoneyInfo(userId);
        if (userMoney == null) return responseData;

        // 没有对应的成品信息
        List<Reap> reaps = reapService.findReapListByName(productName, userId);
        if (null == reaps || reaps.isEmpty()) return responseData;

        Boolean success = reapService.exchangeMogubi(userId, userMoney, reaps);
        if (success)
            return responseData
                    .setMessage(MessageEnum.SUCCESS.getMessage())
                    .setState(MessageEnum.SUCCESS.getState())
                    .setData("兑换成功");
        return responseData;
    }

    /**
     * 蘑菇币支付
     */
    @PostMapping("/paymentMGB")
    public ResponseData<String> paymentMGB(@LoginUserId Integer userId, @RequestBody MallOrder mallOrder) {

        ResponseData<String> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), "支付失败");

        // 参数错误
        if (mallOrder == null || mallOrder.getOrderNumber() == null || mallOrder.getAddressId() == null)
            return responseData;

        // 收货地址是否存在
        UserAddress address = userAddressDAO.selectByIdAndUserId(mallOrder.getAddressId(), userId, false);
        if (null == address) return responseData;

        // 订单物流号是否正确
        MallOrder order = mallOrderService.findByIdAndNum(userId, mallOrder.getOrderNumber());
        if (order == null || !order.getTotalCoins().equals(mallOrder.getTotalCoins()) || order.getState() != 0)
            return responseData;

        // 用户蘑菇币是否足够
        SaleCoin saleCoin = saleCoinService.findById(userId);
        if (saleCoin == null || saleCoin.getCoinCount() < mallOrder.getTotalCoins()) return responseData;

        // 更新用户蘑菇币
        saleCoin.setCoinCount(saleCoin.getCoinCount() - mallOrder.getTotalCoins());
        UserSaleCoin userSaleCoin = new UserSaleCoin();
        userSaleCoin.setSaleCoin(saleCoin);
        userSaleCoin.setUserId(userId);
        userSaleCoin.setAffectCoin(-mallOrder.getTotalCoins());
        userSaleCoin.setAffectType(2);
        userSaleCoin.setAffectDetailId(order.getId().toString());

        // 订单是否成功
        Boolean success = mallOrderService.orderSuccess(order, userSaleCoin);
        if (success)
            return responseData.setMessage(MessageEnum.SUCCESS.getMessage())
                    .setState(MessageEnum.SUCCESS.getState())
                    .setData("支付成功");

        return responseData;
    }

    /**
     * 兑换券
     */
    @PostMapping("/exchangeQ")
    public ResponseData<String> exchangeQ(@LoginUserId Integer userId, @RequestBody PageSearch<Integer> pageSearch) {

        ResponseData<String> responseData = new ResponseData<>(MessageEnum.ERROR.getMessage(), MessageEnum.ERROR.getState(), "兑换失败");

        // 参数错误
        Integer fertilizerId = pageSearch.getWhere();
        if (userId == null || fertilizerId == null) return responseData;

        // 券不存在
        Fertilizer fertilizer = fertilizerService.findById(fertilizerId);
        if (fertilizer == null || fertilizer.getCoinFertilizer() == null || fertilizer.getCoinFertilizer() <= 0)
            return responseData;

        // 蘑菇币不够
        SaleCoin saleCoin = saleCoinService.findById(userId);
        if (saleCoin == null || saleCoin.getCoinCount() == null || saleCoin.getCoinCount() < fertilizer.getCoinFertilizer())
            return responseData;

        Boolean success = reapService.exchangeFertilizer(userId, fertilizerId, saleCoin, fertilizer);
        if (success)
            return responseData
                    .setMessage(MessageEnum.SUCCESS.getMessage())
                    .setState(MessageEnum.SUCCESS.getState())
                    .setData("兑换成功");
        return responseData;
    }

    /**
     * 兑换记录
     */
    @PostMapping("/exchangeLog")
    public PageResult<ExchangeInfo> exchangeLog(@LoginUserId Integer userId, @RequestBody PageSearch<Integer> pageSearch) {
        Integer page = pageSearch.getPage();
        Integer size = pageSearch.getSize();
        Integer where = pageSearch.getWhere();
        // 根据where，显示不同的记录日志
        PageResult<ExchangeInfo> result = null;
        if (where == 1) {
            result = reapService.showReapLog(page, size, userId);
        } else if (where == 2) {
            result = mallProductService.showProductLog(page, size, userId);
        } else if (where == 3) {
            result = fertilizerService.showFertilizerLog(page, size, userId);
        }
        return result;
    }

    /**
     * 用户蘑菇币
     */
    @GetMapping("/getUserCoin")
    public ResponseData<Integer> getUserCoin(@LoginUserId Integer userId) {
        SaleCoin saleCoin = saleCoinService.findById(userId);
        if (saleCoin == null) {
            saleCoin = new SaleCoin();
            saleCoin.setUserId(userId);
            saleCoin.setCoinCount(0);
            saleCoinDao.insert(saleCoin);
        }
        return new ResponseData<>(MessageEnum.SUCCESS.getMessage(), MessageEnum.SUCCESS.getState(), saleCoin.getCoinCount());
    }
}
