package com.moguying.plant.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public enum InterestUtil {
    INSTANCE;

    Logger log = LoggerFactory.getLogger(InterestUtil.class);
    //以360为除数
    private BigDecimal days = new BigDecimal(360);


    public BigDecimal calInterest(BigDecimal money, BigDecimal rate, Integer day) {

        BigDecimal r = rate.divide(new BigDecimal("100.00"), 4, BigDecimal.ROUND_DOWN);
        BigDecimal m = money.multiply(r);
        return m.multiply(new BigDecimal(day)).divide(days, 2, BigDecimal.ROUND_DOWN);
    }

    public BigDecimal calAmount(Integer count, BigDecimal perPrice) {
        BigDecimal c = new BigDecimal(count);
        return c.multiply(perPrice).setScale(2, BigDecimal.ROUND_DOWN);
    }


    public BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
        return b1.multiply(b2).setScale(2, BigDecimal.ROUND_DOWN);
    }

    public BigDecimal divide(BigDecimal b1, BigDecimal b2) {
        return b1.divide(b2, 2, BigDecimal.ROUND_DOWN);
    }

}
