package com.moguying.plant.core.aop;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.annotation.TriggerEvent;
import com.moguying.plant.core.entity.ResultData;
import com.moguying.plant.core.entity.TriggerEventResult;
import com.moguying.plant.core.entity.system.vo.InnerMessage;
import com.moguying.plant.core.service.fertilizer.FertilizerService;
import com.moguying.plant.core.service.user.UserMessageService;
import com.moguying.plant.mq.sender.PhoneMessageSender;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TriggerEventAop  {

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private FertilizerService fertilizerService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @AfterReturning(pointcut = "@annotation(message)", returning = "retVal")
    private void addMessage(Object retVal, TriggerEvent message){
        if(retVal instanceof ResultData){
            ResultData resultData = (ResultData) retVal;

            if(resultData.getMessageEnum().equals(MessageEnum.SUCCESS)){
                if(resultData.getData() instanceof TriggerEventResult) {
                    TriggerEventResult<?> triggerEventResult = (TriggerEventResult<?>) resultData.getData();
                    if(triggerEventResult.getData() instanceof  InnerMessage) {
                        InnerMessage innerMessage = (InnerMessage) triggerEventResult.getData();
                        userMessageService.addMessage(innerMessage, message.action());
                    }
                    fertilizerService.distributeFertilizer(message.action(), triggerEventResult);
                    if(message.action().equals("plant"))
                        rabbitTemplate.convertAndSend("plant.topic","plant.lottery",triggerEventResult.getData());
                }
            }

        }
    }
}
