package com.moguying.plant.core.service.order;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;
import com.moguying.plant.core.entity.dto.User;
import com.moguying.plant.core.entity.dto.payment.request.PaymentRequest;
import com.moguying.plant.core.entity.dto.payment.request.WebHtmlPayRequest;
import com.moguying.plant.core.entity.dto.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.vo.*;

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
