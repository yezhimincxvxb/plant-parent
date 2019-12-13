package com.moguying.plant.core.config;

import com.alibaba.fastjson.support.spring.messaging.MappingFastJsonMessageConverter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;


@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    public final static String PHONE_MESSAGE = "phone.message";

    public final static String PLANT_ACTION = "plant.#";

    public final static String FERTILIZER_ACTION = "#.fertilizer";




    @Bean
    public Queue phoneMessageQueue() {
        return new Queue("phone.message");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("phone.message.direct");
    }

    @Bean
    public Binding bindingExchangeMessage(Queue phoneMessageQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(phoneMessageQueue).to(directExchange).with(RabbitConfig.PHONE_MESSAGE);
    }

    @Bean
    public Queue plantLotteryQueue(){
        return new Queue("plant.lottery");
    }

    @Bean
    public Queue lotteryFertilizerQueue(){
        return new Queue("lottery.fertilizer");
    }


    @Bean
    public TopicExchange afterPlantExchange() {
        return new TopicExchange("plant.topic");
    }


    @Bean
    public Binding bindingTopicExchangeMessageB(Queue plantLotteryQueue,TopicExchange afterPlantExchange) {
        return BindingBuilder.bind(plantLotteryQueue).to(afterPlantExchange).with(RabbitConfig.PLANT_ACTION);
    }

    @Bean
    public Binding bindingTopicExchangeMessageA(Queue lotteryFertilizerQueue,TopicExchange afterPlantExchange) {
        return BindingBuilder.bind(lotteryFertilizerQueue).to(afterPlantExchange).with(RabbitConfig.FERTILIZER_ACTION);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }


    @Bean
    public MessageHandlerMethodFactory defaultMessageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        defaultMessageHandlerMethodFactory.setMessageConverter(mappingFastJsonMessageConverter());
        return defaultMessageHandlerMethodFactory;
    }

    @Bean
    public MappingFastJsonMessageConverter mappingFastJsonMessageConverter(){
        return new MappingFastJsonMessageConverter();
    }



    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(defaultMessageHandlerMethodFactory());
    }
}
