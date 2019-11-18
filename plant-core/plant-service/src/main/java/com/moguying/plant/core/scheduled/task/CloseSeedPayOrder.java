package com.moguying.plant.core.scheduled.task;

import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import com.moguying.plant.utils.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CloseSeedPayOrder extends CloseOrderItem {

    private Logger log = LoggerFactory.getLogger(CloseSeedPayOrder.class);


    public CloseSeedPayOrder(Integer id, Long expireTime) {
        super(id, (int) (expireTime / 60));
    }

    public CloseSeedPayOrder(Integer id, int loop) {
        super(id, loop);
    }

    @Override
    public void run() {
        log.debug("close seed pay order:{}", toString());
        SeedOrderDetailService seedOrderDetailService = ApplicationContextUtil.getBean(SeedOrderDetailService.class);
        seedOrderDetailService.seedOrderCancel(id, null);
    }
}
