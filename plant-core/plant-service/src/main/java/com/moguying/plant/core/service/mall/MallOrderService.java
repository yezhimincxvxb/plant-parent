package com.moguying.plant.core.service.mall;

import com.moguying.plant.core.entity.PageResult;
import com.moguying.plant.core.entity.PageSearch;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.coin.UserSaleCoin;
import com.moguying.plant.core.entity.mall.MallOrder;
import com.moguying.plant.core.entity.mall.vo.CancelOrder;
import com.moguying.plant.core.entity.mall.vo.TraceInfoParam;
import com.moguying.plant.core.entity.payment.response.PayResponse;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.seed.vo.SendPayOrderResponse;
import com.moguying.plant.core.entity.user.vo.UserMallOrder;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface MallOrderService {


    PageResult<MallOrder> mallOrderList(Integer page, Integer size, MallOrder where);


    PageResult<UserMallOrder> userMallOrderListByState(Integer page, Integer size, Integer userId, Integer state);


    void setUserMallOrderItemList(List<UserMallOrder> orderList, Integer userId);


    Integer saveOrder(MallOrder order);


    MallOrder selectOrderById(Integer id);


    ResultData<PaymentResponse<PayResponse>> payOrder(SendPayOrder payOrder, Integer userId);


    ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId);


    ResultData<Integer> cancelOrder(CancelOrder cancelOrder, Integer userId);

    String getStateStr(Integer state);


    ResultData<Integer> orderRefund(Integer orderId, Integer userId);


    List<MallOrder> needPayOrders();


    MallOrder mallOrderDetail(Integer id);

    String synQueryData(TraceInfoParam traceInfoParam);


    MallOrder findByIdAndNum(Integer userId, String orderNumber);


    Boolean orderSuccess(MallOrder order, UserSaleCoin userSaleCoin);

    Integer getMallOrderNum(Integer state);

    void downloadExcel(Integer userId, PageSearch<MallOrder> search, HttpServletRequest request);

    Integer getMallOrderUserNum(Integer state);

    BigDecimal getMallOrderAmount(Integer state);
}
