package com.moguying.plant.core.service.payment;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.entity.dto.payment.PaymentInfo;

public interface PaymentInfoService {

    @DataSource("write")
    Integer updateNotifyResponse(PaymentInfo paymentInfo);
}
