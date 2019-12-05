package com.moguying.plant.mq;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.seed.vo.PlantOrderResponse;
import com.moguying.plant.mq.sender.PhoneMessageSender;
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
     * 添加抽奖信息
     */
    @RabbitListener(queues = "plant.lottery")
    @RabbitHandler
    public void addLottery(PlantOrderResponse resultData){

        Optional<PlantOrderResponse> optional = Optional.ofNullable(resultData);
        if(optional.isPresent()) {
            redisTemplate.opsForList().leftPush("plant:lottery:".concat(resultData.getUserId().toString()),
                    resultData.getReapId().toString());
        }
    }

}
