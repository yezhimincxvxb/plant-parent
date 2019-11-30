package com.moguying.plant.mq;

import com.alibaba.fastjson.JSON;
import com.moguying.plant.mq.sender.PhoneMessageSender;
import com.moguying.plant.utils.message.MessageSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "moguying.phone.message")
@Slf4j
public class PhoneMessageReceiver {

    @Autowired
    private MessageSendUtil sendUtil;

    @RabbitHandler
    public void  process(String json){
        PhoneMessageSender phoneMessageSender = JSON.parseObject(json, PhoneMessageSender.class);
        sendUtil.sendNormal(phoneMessageSender.getPhone(),phoneMessageSender.getContent());
    }
}
