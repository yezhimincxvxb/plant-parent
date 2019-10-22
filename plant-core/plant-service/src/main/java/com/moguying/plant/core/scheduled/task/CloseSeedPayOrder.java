package com.moguying.plant.core.scheduled.task;

import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class CloseSeedPayOrder extends CloseOrderItem {

    private Logger log = LoggerFactory.getLogger(CloseSeedPayOrder.class);


    public CloseSeedPayOrder(Integer id,Long expireTime) {
        super(id,(int)(expireTime / 60));
    }

    public CloseSeedPayOrder(Integer id, int loop) {
        super(id, loop);
    }

    @Override
    public void run() {
        log.debug("close seed pay order:{}",toString());
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        SeedOrderDetailService seedOrderDetailService = context.getBean(SeedOrderDetailService.class);
        seedOrderDetailService.seedOrderCancel(id,null);
    }
}
