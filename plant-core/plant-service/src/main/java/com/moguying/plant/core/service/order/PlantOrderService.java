package com.moguying.plant.core.service.order;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.coin.vo.ExcReap;
import com.moguying.plant.core.entity.seed.SeedOrderDetail;
import com.moguying.plant.core.entity.seed.vo.*;
import com.moguying.plant.core.entity.user.User;
import com.moguying.plant.core.entity.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.payment.request.WebHtmlPayRequest;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;

public interface PlantOrderService {

    @DataSource("write")
    ResultData<BuyOrderResponse> plantOrder(BuyOrder order, Integer userId);

    @DataSource("write")
    ResultData<SendPayOrderResponse> checkPayOrder(SendPayOrder payOrder, Integer userId);

    @DataSource("write")
    ResultData<PaymentResponse> payOrder(SendPayOrder payOrder, Integer userId);

    @DataSource("write")
    ResultData<Integer> payOrderSuccess(SeedOrderDetail orderDetail, User userInfo);

    @DataSource("write")
    ResultData<TriggerEventResult<PlantOrderResponse>> plantSeed(Integer userId, PlantOrder plantOrder);

    @DataSource("write")
    ResultData<Integer> plantReapExchange(Integer reapId, ExcReap excReap);

    @DataSource("write")
    PaymentRequest<WebHtmlPayRequest> webHtmlPayRequestData(SeedOrderDetail seedOrderDetail);

}
