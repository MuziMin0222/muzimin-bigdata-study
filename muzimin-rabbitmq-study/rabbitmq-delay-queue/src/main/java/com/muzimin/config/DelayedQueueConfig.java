package com.muzimin.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author: 李煌民
 * @date: 2024-04-24 19:14
 **/
@Configuration
public class DelayedQueueConfig {
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    @Bean(DELAYED_QUEUE_NAME)
    Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //自定义交换机 我们在这里定义的是一个延迟交换机
    @Bean(DELAYED_EXCHANGE_NAME)
    CustomExchange delayedExchange() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean(DELAYED_ROUTING_KEY)
    Binding delayedBingind(
            @Qualifier(DELAYED_QUEUE_NAME) Queue queueName,
            @Qualifier(DELAYED_EXCHANGE_NAME) CustomExchange exchange
    ) {
        return BindingBuilder.bind(queueName).to(exchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
