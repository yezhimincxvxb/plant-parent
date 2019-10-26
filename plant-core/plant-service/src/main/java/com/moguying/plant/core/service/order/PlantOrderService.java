package com.moguying.plant.core.service.order;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.coin.vo.ExcReap;
import com.moguying.plant.core.entity.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.payment.request.WebHtmlPayRequest;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.*;
import com.moguying.plant.core.entity.user.User;

public interface PlantOrderService {

    ResultData<BuyOrderResponse> plantOrder(BuyOrder order, Integer userId);

    ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId);

    ResultData<PaymentResponse> payOrder(SendPayOrder payOrder, Integer userId);

    ResultData<Integer> payOrderSuccess(SeedOrderDetail orderDetail, User userInfo);

    ResultData<TriggerEventResult<PlantOrderResponse>> plantSeed(Integer userId, PlantOrder plantOrder);

    ResultData<Integer> plantReapExchange(Integer reapId, ExcReap excReap);

    PaymentRequest<WebHtmlPayRequest> webHtmlPayRequestData(SeedOrderDetail seedOrderDetail);

}
