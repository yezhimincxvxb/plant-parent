package com.moguying.plant.mq;

import com.moguying.plant.constant.ActivityEnum;
import com.moguying.plant.core.entity.mq.FertilizerSender;
import com.moguying.plant.core.entity.mq.PhoneMessageSender;
import com.moguying.plant.core.entity.seed.vo.PlantOrderResponse;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.utils.message.MessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class RabbitMqMessageHandler {



    @Autowired
    private MessageSendUtil sendUtil;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FertilizerService fertilizerService;

    /**
     * 发送短信
     * @param sender
     */
    @RabbitListener(queues = "phone.message")
    @RabbitHandler
    public void  process(PhoneMessageSender sender){
        Optional<PhoneMessageSender> senderOptional = Optional.ofNullable(sender);
        if(senderOptional.isPresent())
            sendUtil.sendNormal(sender.getPhone(),sender.getContent());
    }


    /**
     * 抽奖发券
     * @param fertilizerSender
     */
    @RabbitListener(queues = "lottery.fertilizer")
    @RabbitHandler
    public void addFertilizer(FertilizerSender fertilizerSender) {
        fertilizerService.distributeFertilizer(fertilizerSender.getEvent(),fertilizerSender.getUserId());
    }




    /**
     * 添加抽奖信息
     */
    @RabbitListener(queues = "plant.lottery")
    @RabbitHandler
    public void addLottery(PlantOrderResponse resultData) {

        Optional<PlantOrderResponse> optional = Optional.ofNullable(resultData);
        if(optional.isPresent()) {
            String countKey = ActivityEnum.LOTTERY_COUNT_KEY_PRE.getMessage().concat(resultData.getUserId().toString());
            String count = Optional.ofNullable(redisTemplate.opsForValue().get(countKey)).orElse("0");
            if(Integer.parseInt(count) <= 0) return;
            redisTemplate.opsForValue().decrement(countKey);
            redisTemplate.opsForList().leftPush(ActivityEnum.LOTTERY_KEY_PRE.getMessage().concat(resultData.getUserId().toString()),
                    resultData.getReapId().toString());
        }
    }

}
