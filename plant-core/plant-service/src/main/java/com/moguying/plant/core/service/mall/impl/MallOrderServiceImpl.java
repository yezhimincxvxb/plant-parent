package com.moguying.plant.core.service.mall.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moguying.plant.constant.MallEnum;
import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.constant.MoneyOpEnum;
import com.moguying.plant.constant.SystemEnum;
import com.moguying.plant.core.dao.fertilizer.UserFertilizerDAO;
import com.moguying.plant.core.dao.mall.MallCarDAO;
import com.moguying.plant.core.dao.mall.MallOrderDAO;
import com.moguying.plant.core.dao.mall.MallOrderDetailDAO;
import com.moguying.plant.core.dao.mall.MallProductDAO;
import com.moguying.plant.core.dao.user.UserAddressDAO;
import com.moguying.plant.core.dao.user.UserDAO;
import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.account.UserMoney;
import com.moguying.plant.core.entity.coin.SaleCoin;
import com.moguying.plant.core.entity.coin.UserSaleCoin;
import com.moguying.plant.core.entity.fertilizer.UserFertilizer;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.vo.CancelOrder;
import com.moguying.plant.core.entity.mall.vo.MallOrderSearch;
import com.moguying.plant.core.entity.mall.vo.OrderItem;
import com.moguying.plant.core.entity.mall.vo.TraceInfoParam;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.seed.vo.SendPayOrderResponse;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.user.UserMoneyOperator;
import com.moguying.plant.core.entity.user.vo.UserMallOrder;
import com.moguying.plant.core.service.account.UserMoneyService;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.core.service.payment.PaymentApiService;
import com.moguying.plant.core.service.payment.PaymentService;
import com.moguying.plant.core.service.reap.SaleCoinService;
import com.moguying.plant.core.service.user.UserMessageService;
import com.moguying.plant.utils.CFCARAUtil;
import com.moguying.plant.utils.CurlUtil;
import com.moguying.plant.utils.PasswordUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MallOrderServiceImpl implements MallOrderService {

    private static Map<Integer, String> stateMap = new HashMap<>();

    static {
        stateMap.put(MallEnum.ORDER_NEED_PAY.getState(), MallEnum.ORDER_NEED_PAY.getStateStr());
        stateMap.put(MallEnum.ORDER_HAS_PAY.getState(), MallEnum.ORDER_HAS_PAY.getStateStr());
        stateMap.put(MallEnum.ORDER_HAS_SEND.getState(), MallEnum.ORDER_HAS_SEND.getStateStr());
        stateMap.put(MallEnum.ORDER_HAS_DONE.getState(), MallEnum.ORDER_HAS_DONE.getStateStr());
        stateMap.put(MallEnum.ORDER_HAS_CLOSE.getState(), MallEnum.ORDER_HAS_CLOSE.getStateStr());
        stateMap.put(MallEnum.ORDER_HAS_CANCEL.getState(), MallEnum.ORDER_HAS_CANCEL.getStateStr());
    }

    /**
     * 实时查询请求地址
     */
    @Value("${express.query.url}")
    private String expressQueryUrl;

    @Autowired
    private MallOrderDAO mallOrderDAO;

    @Autowired
    private MallOrderDetailDAO mallOrderDetailDAO;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentApiService paymentApiService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MallCarDAO mallCarDAO;

    @Autowired
    private MallProductDAO mallProductDAO;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private UserMoneyService userMoneyService;

    @Autowired
    private UserAddressDAO userAddressDAO;

    @Autowired
    private SaleCoinService saleCoinService;

    @Autowired
    private UserFertilizerDAO userFertilizerDAO;

    @Override
    @DS("read")
    public PageResult<MallOrder> mallOrderList(Integer page, Integer size, MallOrderSearch where) {
        IPage<MallOrder> pageResult = mallOrderDAO.selectSelective(new Page<>(page, size), where);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("write")
    public Integer saveOrder(MallOrder order) {
        if (null != order.getId()) {
            mallOrderDAO.updateById(order);
        } else {
            mallOrderDAO.insert(order);
        }
        return order.getId();
    }

    @Override
    @DS("read")
    public MallOrder selectOrderById(Integer id) {
        return mallOrderDAO.selectById(id);
    }


    @Override
    @DS("read")
    public PageResult<UserMallOrder> userMallOrderListByState(Integer page, Integer size, Integer userId, Integer state) {
        IPage<UserMallOrder> pageResult = mallOrderDAO.userOrderListByState(new Page<>(page, size), userId, state);
        return new PageResult<>(pageResult.getTotal(), pageResult.getRecords());
    }

    @Override
    @DS("read")
    public void setUserMallOrderItemList(List<UserMallOrder> orderList, Integer userId) {
        for (UserMallOrder order : orderList) {
            List<OrderItem> orderItems = mallOrderDetailDAO.selectDetailListByOrderId(order.getId(), userId);
            order.setOrderItems(orderItems);
            order.setProductCount(orderItems.size());
            order.setStateStr(stateMap.get(order.getState()));
        }
    }

    @Override
    public String getStateStr(Integer state) {
        return stateMap.get(state);
    }


    @Transactional
    @Override
    @DS("write")
    public ResultData<PaymentResponse> payOrder(SendPayOrder payOrder, Integer userId) {
        ResultData<PaymentResponse> resultData = new ResultData<>(MessageEnum.ERROR, null);
        MallOrder mallOrder = mallOrderDAO.selectById(payOrder.getOrderId());
        if (null == mallOrder || null != mallOrder.getPayTime())
            return resultData.setMessageEnum(MessageEnum.MALL_ORDER_NOT_EXISTS);
        User userInfo = userDAO.userInfoById(userId);
        MallOrder update = new MallOrder();
        update.setId(payOrder.getOrderId());
        update.setPayTime(new Date());
        update.setState(MallEnum.ORDER_HAS_PAY.getState());
        resultData = paymentApiService.payOrder(payOrder, mallOrder, userInfo);
        if (!resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (resultData.getMessageEnum().equals(MessageEnum.ERROR)) {
                ((MallOrderService) AopContext.currentProxy()).cancelOrder(new CancelOrder(mallOrder.getId(), resultData.getMessageEnum().getMessage()), userId);
            }
            return resultData;
        }
        if (resultData.getMessageEnum().equals(MessageEnum.SUCCESS)) {
            //删除购物车中的项目
            List<OrderItem> mallOrderDetails = mallOrderDetailDAO.selectDetailListByOrderId(mallOrder.getId(), userId);
            mallCarDAO.deleteItemByRange(mallOrderDetails, userId);
            //更新详情
            mallOrderDAO.updateById(update);
            //发送站内信
            InnerMessage message = new InnerMessage();
            message.setUserId(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            message.setTime(sdf.format(new Date()));
            message.setPhone(userInfo.getPhone());
            userMessageService.addMessage(message, SystemEnum.PHONE_MESSAGE_MALL_BUY_TYPE.getStateName());
        }
        return resultData;
    }

    @Override
    @DS("write")
    public ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId) {
        return paymentService.checkPayOrder(payOrder, userId, mallOrderDAO, MallOrder.class);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    @DS(value = "write")
    public ResultData<Integer> cancelOrder(CancelOrder cancelOrder, Integer userId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        // 更状态
        MallOrder mallOrder = mallOrderDAO.selectById(cancelOrder.getId());
        if (null == mallOrder || !mallOrder.getState().equals(MallEnum.ORDER_NEED_PAY.getState()))
            return resultData.setMessageEnum(MessageEnum.MALL_ORDER_NOT_EXISTS);

        List<OrderItem> orderItems = mallOrderDetailDAO.selectDetailListByOrderId(cancelOrder.getId(), userId);
        if (null == orderItems || orderItems.size() == 0)
            return resultData.setMessageEnum(MessageEnum.MALL_ORDER_ITEM_EMPTY);
        for (OrderItem item : orderItems) {
            // 返库存
            mallProductDAO.updateProductHasCountById(-item.getBuyCount(), item.getProductId());
        }
        MallOrder update = new MallOrder();
        update.setId(mallOrder.getId());
        update.setCloseTime(new Date());
        update.setCancelReason(cancelOrder.getCancelReason());
        if (null != userId)
            update.setState(MallEnum.ORDER_HAS_CANCEL.getState());
        else
            update.setState(MallEnum.ORDER_HAS_CLOSE.getState());
        if (mallOrderDAO.updateById(update) > 0)
            return resultData.setMessageEnum(MessageEnum.SUCCESS);
        return resultData;
    }

    @Transactional
    @Override
    @DS("write")
    public ResultData<Integer> orderRefund(Integer orderId, Integer userId) {
        ResultData<Integer> resultData = new ResultData<>(MessageEnum.ERROR, null);
        MallOrder order = mallOrderDAO.selectById(orderId);
        if (null == order)
            return resultData.setMessageEnum(MessageEnum.MALL_ORDER_NOT_EXISTS);
        if (!order.getState().equals(MallEnum.ORDER_HAS_PAY.getState()))
            return resultData.setMessageEnum(MessageEnum.MALL_ORDER_CAN_NOT_REFUND);

        // 返券
        if (Objects.nonNull(order.getFertilizerId())) {
            UserFertilizer userFertilizer = userFertilizerDAO.selectById(order.getFertilizerId());
            if (Objects.nonNull(userFertilizer)) {
                userFertilizer.setState(0);
                if (userFertilizerDAO.updateById(userFertilizer) < 0)
                    return resultData.setMessageEnum(MessageEnum.RETURN_FERTILIZER_ERROR);
            }
        }

        // 退库存
        List<OrderItem> orderItems = mallOrderDetailDAO.selectDetailListByOrderId(orderId, userId);
        for (OrderItem item : orderItems) {
            // 返库存
            mallProductDAO.updateProductHasCountById(item.getBuyCount(), item.getProductId());
        }

        // 退款
        if (order.getBuyAmount() != null && order.getBuyAmount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal payAmount = order.getCarPayAmount().add(order.getAccountPayAmount());
            UserMoneyOperator operator = new UserMoneyOperator();
            operator.setOpType(MoneyOpEnum.BUY_CANCEL);
            operator.setOperationId(order.getOrderNumber());
            UserMoney money = new UserMoney(userId);
            money.setAvailableMoney(payAmount);
            operator.setUserMoney(money);
            if (userMoneyService.updateAccount(operator) != null) {
                ResultData<Integer> cancelResult = cancelOrder(new CancelOrder(orderId, MessageEnum.MALL_ORDER_REFUND_CLOSED.getMessage()), userId);
                if (cancelResult.getMessageEnum().equals(MessageEnum.SUCCESS)) {
                    return resultData.setMessageEnum(MessageEnum.SUCCESS);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return resultData;
                }
            }
        }

        // 退蘑菇币
        if (order.getTotalCoins() != null && order.getTotalCoins() > 0) {

            SaleCoin saleCoin = saleCoinService.findById(userId);
            if (saleCoin == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return resultData;
            }

            // 更新蘑菇币并生成蘑菇币日志
            saleCoin.setCoinCount(saleCoin.getCoinCount() + order.getTotalCoins());
            UserSaleCoin userSaleCoin = new UserSaleCoin();
            userSaleCoin.setSaleCoin(saleCoin);
            userSaleCoin.setUserId(userId);
            userSaleCoin.setAffectCoin(order.getTotalCoins());
            userSaleCoin.setAffectType(2);
            userSaleCoin.setAffectDetailId(order.getId().toString());
            if (saleCoinService.updateSaleCoin(userSaleCoin) != null)
                return resultData.setMessageEnum(MessageEnum.SUCCESS);
        }
        return resultData;
    }


    @Override
    @DS(value = "read")
    public List<MallOrder> needPayOrders() {
        MallOrder where = new MallOrder();
        where.setState(MallEnum.ORDER_NEED_PAY.getState());
        return mallOrderDAO.selectList(new QueryWrapper<>(where));
    }

    @Override
    @DS("read")
    public MallOrder mallOrderDetail(Integer id) {
        MallOrder mallOrder = mallOrderDAO.selectById(id);
        if (null == mallOrder)
            return null;
        mallOrder.setAddress(userAddressDAO.selectByIdAndUserId(mallOrder.getAddressId(), mallOrder.getUserId(), null));
        mallOrder.setDetails(mallOrderDetailDAO.selectDetailListByOrderId(mallOrder.getId(), mallOrder.getUserId()));
        return mallOrder;
    }


    /**
     * 实时查询快递单号
     */
    @Override
    public String synQueryData(TraceInfoParam traceInfoParam) {

        // 公司编号、授权key
        String customer = "BFF7093E590DC639EC851CE58070B80C";
        String key = "RrVhZVGw1884";

        // 签名， 用于验证身份， 按 param + key + customer 的顺序进行MD5加密
        String str = JSON.toJSONString(traceInfoParam) + key + customer;
        String sign = PasswordUtil.INSTANCE.encode(str.getBytes(), null);

        // Map封装数据
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customer);
        params.put("sign", sign.toUpperCase());
        params.put("param", JSON.toJSONString(traceInfoParam));

        // 拼接成字符串
        String reqStr = CFCARAUtil.joinMapValue(params, '&');

        // 请求第三方接口获取返回数据
        return CurlUtil.INSTANCE.httpRequest(expressQueryUrl, reqStr, "POST");
    }

    @Override
    public MallOrder findByIdAndNum(Integer userId, String orderNumber) {
        return mallOrderDAO.findByIdAndNum(userId, orderNumber);
    }

    @Override
    @Transactional
    @DS("write")
    public Boolean orderSuccess(MallOrder order, UserSaleCoin userSaleCoin) {
        // 更新蘑菇币
        if (saleCoinService.updateSaleCoin(userSaleCoin) == null) return false;
        // 更新订单状态
        order.setState(1);
        order.setPayTime(new Date());
        return mallOrderDAO.updateById(order) > 0;
    }
}
