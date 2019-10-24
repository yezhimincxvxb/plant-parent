package com.moguying.plant.core.scheduled;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.dto.MallOrder;
import com.moguying.plant.core.entity.dto.SeedOrderDetail;
import com.moguying.plant.core.entity.vo.CancelOrder;
import com.moguying.plant.core.scheduled.task.CloseMallPayOrder;
import com.moguying.plant.core.scheduled.task.CloseOrderItem;
import com.moguying.plant.core.scheduled.task.CloseSeedPayOrder;
import com.moguying.plant.core.service.mall.MallOrderService;
import com.moguying.plant.core.service.seed.SeedOrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class CloseOrderScheduled {

    private Logger log = LoggerFactory.getLogger(CloseOrderScheduled.class);

    private static final List<Set<CloseOrderItem>> CLOSE_LOOP = Collections.synchronizedList(new ArrayList<>(60));

    static {
        for(int i = 0;i< 60;i++){
            CLOSE_LOOP.add(new HashSet<>());
        }
    }

    @Value("${order.expire.time}")
    private int expireTime;

    private int currentIndex = 0;

    @Autowired
    private SeedOrderDetailService seedOrderDetailService;

    @Autowired
    private MallOrderService mallOrderService;

    /**
     * 启动时，加载数据库中记录
     */
    @PostConstruct
    public void initCloseLoop(){
        List<SeedOrderDetail> needPayList = seedOrderDetailService.needPayOrderList();
        for(SeedOrderDetail order : needPayList){
            long left = expireTime - ((new Date().getTime() - order.getAddTime().getTime()) / 1000);
            if(left <= 0) {
                seedOrderDetailService.seedOrderCancel(order.getId(), null);
            } else {
                addCloseItem(new CloseSeedPayOrder(order.getId(),(int)(left / 60)),(int)(left % 60));
            }
        }

        List<MallOrder> mallOrders = mallOrderService.needPayOrders();
        for(MallOrder mallOrder : mallOrders){
            long left = expireTime  - ((new Date().getTime() - mallOrder.getAddTime().getTime()) / 1000);
            if(left <=0){
                mallOrderService.cancelOrder(new CancelOrder(mallOrder.getId(), MessageEnum.MALL_ORDER_TIMEOUT_CLOSED.getMessage()),null);
            } else {
                addCloseItem(new CloseMallPayOrder(mallOrder.getId(),(int)(left / 60)),(int)(left % 60));
            }

        }
    }


    /**
     * 每秒向环中移一格查找要关闭的order
     */
    @Scheduled(fixedRate = 1000)
    public void close(){
        currentIndex = currentIndex % 60;
        if(CLOSE_LOOP.size() > 0) {
            Set<CloseOrderItem> closeOrderItems = CLOSE_LOOP.get(currentIndex);
            if (null != closeOrderItems && !closeOrderItems.isEmpty()) {
                for (CloseOrderItem item : closeOrderItems) {
                    if (item.getLoop() == 0) {
                        Thread taskThread = new Thread(item);
                        taskThread.start();
                        closeOrderItems.remove(item);
                    } else {
                        log.debug("item:{}",item);
                        item.setLoop(item.getLoop() - 1);
                    }
                }
            }
        }
        currentIndex++;
    }

    public void addCloseItem(CloseOrderItem item){
        addCloseItem(item,currentIndex % 60);
    }

    /**
     * 添加关单对象
     * @param item
     */
    public void addCloseItem(CloseOrderItem item,int currentIndex ){
        Set<CloseOrderItem> closeOrderItems = CLOSE_LOOP.get(currentIndex);
        if(null != closeOrderItems) {
            closeOrderItems.add(item);
            log.debug("add to index:{}",currentIndex);
            CLOSE_LOOP.set(currentIndex,closeOrderItems);
        }
    }


    /**
     * 删除关单对象
     * @param item
     */
    public void removeCloseItem(CloseOrderItem item){

    }



}
