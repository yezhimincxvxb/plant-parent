package com.moguying.plant.core.service.payment.impl;

import com.moguying.plant.core.annotation.DataSource;
import com.moguying.plant.core.dao.payment.PaymentInfoDAO;
import com.moguying.plant.core.entity.payment.PaymentInfo;
import com.moguying.plant.core.service.payment.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoDAO paymentInfoDAO;

    @Override
    @DataSource("write")
    public Integer updateNotifyResponse(PaymentInfo paymentInfo) {
        return paymentInfoDAO.updateNotifyResponseByOrderNumber(paymentInfo);
    }
}
