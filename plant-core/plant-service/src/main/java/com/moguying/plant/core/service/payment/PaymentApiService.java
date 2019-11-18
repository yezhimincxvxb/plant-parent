package com.moguying.plant.core.service.payment;

import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.payment.response.PaymentResponse;
import com.moguying.plant.core.entity.seed.vo.SendPayOrder;
import com.moguying.plant.core.entity.system.PayOrder;
import com.moguying.plant.core.entity.user.User;

public interface PaymentApiService {


    ResultData<PaymentResponse> payOrder(SendPayOrder payOrder, PayOrder orderDetail, User userInfo);
}
