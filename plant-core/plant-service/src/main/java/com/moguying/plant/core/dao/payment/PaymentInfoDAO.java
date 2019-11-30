package com.moguying.plant.core.dao.payment;

import com.moguying.plant.core.dao.BaseDAO;
import com.moguying.plant.core.entity.payment.PaymentInfo;
import org.springframework.stereotype.Repository;

/**
 * PaymentInfoDAO继承基类
 */
@Repository
public interface PaymentInfoDAO extends BaseDAO<PaymentInfo> {
    Integer updateNotifyResponseByOrderNumber(PaymentInfo paymentInfo);

}