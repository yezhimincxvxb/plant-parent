package com.moguying.plant.core.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public final static String PHONE_MESSAGE = "moguying.phone.message";

    @Bean
    public Queue phoneMessageQueue() {
        return new Queue(RabbitConfig.PHONE_MESSAGE);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("phoneMessageExchange");
    }

    @Bean
    public Binding bindingExchangeMessage(Queue phoneMessageQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(phoneMessageQueue).to(directExchange).with(RabbitConfig.PHONE_MESSAGE);
    }


}
