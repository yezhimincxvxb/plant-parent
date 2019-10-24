package com.moguying.plant.core.dao.payment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moguying.plant.core.entity.payment.PaymentInfo;
import org.springframework.stereotype.Repository;

/**
 * PaymentInfoDAO继承基类
 */
@Repository
public interface PaymentInfoDAO extends BaseMapper<PaymentInfo> {
    Integer updateNotifyResponseByOrderNumber(PaymentInfo paymentInfo);

}