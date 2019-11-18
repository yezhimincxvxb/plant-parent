package com.moguying.plant.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "moguying.phone.message")
@Slf4j
public class PhoneMessageReceiver {

    @RabbitHandler
    public void  process(String hello){
        log.debug("Receive:=========={}",hello);
    }
}
