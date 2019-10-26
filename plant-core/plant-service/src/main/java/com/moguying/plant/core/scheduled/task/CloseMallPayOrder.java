package com.moguying.plant.core.scheduled.task;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.mall.vo.CancelOrder;
import com.moguying.plant.core.service.mall.MallOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class CloseMallPayOrder extends CloseOrderItem {
    private Logger log = LoggerFactory.getLogger(CloseSeedPayOrder.class);

    public CloseMallPayOrder(Integer id,Long expireTime) {
        super(id,(int)(expireTime / 60));
    }

    public CloseMallPayOrder(Integer id, int loop) {
        super(id, loop);
    }

    @Override
    public void run() {
        log.debug("close mall pay order:{}",toString());
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        MallOrderService mallOrderService = context.getBean(MallOrderService.class);
        mallOrderService.cancelOrder(new CancelOrder(id, MessageEnum.MALL_ORDER_TIMEOUT_CLOSED.getMessage()),null);
    }
}