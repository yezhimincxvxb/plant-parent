package com.moguying.plant.core.service.payment;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.dto.PayOrder;
import com.moguying.plant.core.entity.dto.User;
import com.moguying.plant.core.entity.dto.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.vo.SendPayOrder;

public interface PaymentApiService {

    @DataSource
    ResultData<PaymentResponse> payOrder(SendPayOrder payOrder, PayOrder orderDetail, User userInfo);
}
