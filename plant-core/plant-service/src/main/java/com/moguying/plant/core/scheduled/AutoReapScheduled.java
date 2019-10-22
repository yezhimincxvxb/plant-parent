package com.moguying.plant.core.scheduled;

import com.moguying.plant.core.service.reap.ReapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class AutoReapScheduled {

    @Autowired
    private ReapService reapService;

    @Scheduled(cron = "0 30 8 * * ?")
    public void autoReap(){
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(reapService.autoReap(today) > 0)
            log.debug("{}自动采摘成功",sdf.format(today));
         else
            log.debug("{}自动采摘失败",sdf.format(today));
    }
}
