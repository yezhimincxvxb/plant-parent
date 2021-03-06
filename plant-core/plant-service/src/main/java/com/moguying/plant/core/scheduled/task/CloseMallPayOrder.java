package com.moguying.plant.core.scheduled.task;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.mall.vo.CancelOrder;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseMallPayOrder extends CloseOrderItem {

    public CloseMallPayOrder(Integer id, Long expireTime) {
        super(id, (int) (expireTime / 60));
    }

    public CloseMallPayOrder(Integer id, int loop) {
        super(id, loop);
    }

    @Override
    public void run() {
        log.debug("close mall pay order:{}", super.toString());
        MallOrderService mallOrderService = ApplicationContextUtil.getBean(MallOrderService.class);
        mallOrderService.cancelOrder(new CancelOrder(id, MessageEnum.MALL_ORDER_TIMEOUT_CLOSED.getMessage()), null);
    }
}
