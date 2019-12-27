package com.moguying.plant.core.scheduled.task;

import com.moguying.plant.core.service.bargain.BargainDetailService;
import com.moguying.plant.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseBargainOrder extends CloseOrderItem {

    public CloseBargainOrder(Integer id, Long expireTime) {
        super(id, (int) (expireTime / 60));
    }

    public CloseBargainOrder(Integer id, int loop) {
        super(id, loop);
    }

    @Override
    public void run() {
        log.debug("close bargain order:{}", toString());
        BargainDetailService detailService = ApplicationContextUtil.getBean(BargainDetailService.class);
        detailService.bargainOrderClose(super.id);
    }
}
