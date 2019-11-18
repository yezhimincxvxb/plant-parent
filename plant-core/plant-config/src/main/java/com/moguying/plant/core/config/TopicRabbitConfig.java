package com.moguying.plant.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TopicRabbitConfig {

    public final static String PHONE_MESSAGE = "moguying.phone.message";


    @Bean
    public Queue phoneMessageQueue() {
        return new Queue(TopicRabbitConfig.PHONE_MESSAGE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingExchangeMessage(Queue phoneMessageQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(phoneMessageQueue).to(topicExchange).with(TopicRabbitConfig.PHONE_MESSAGE);
    }

}
