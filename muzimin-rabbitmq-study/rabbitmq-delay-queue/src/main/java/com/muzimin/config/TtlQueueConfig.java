package com.muzimin.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author: 李煌民
 * @date: 2024-04-24 15:56
 **/
@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGES = "x";
    public static final String Y_DEAD_LETTER_EXCHANGE = "y";

    public static final String QUEUE_A = "queue_a";
    public static final String QUEUE_B = "queue_b";
    public static final String QUEUE_C = " queue_c";
    public static final String DEAD_LETTER_QUEUE = "queue_dead";

    public static final String X_TO_QA_RK = "x_to_qa_rk";
    public static final String X_TO_QB_RK = "x_to_qb_rk";
    public static final String Q_TO_QD_RK = "q_to_qd_rk";
    public static final String X_TO_QC_RK = "x_to_qc_rk";

    //声明exchange
    @Bean(X_EXCHANGES)
    public DirectExchange XAExchange() {
        return new DirectExchange(X_EXCHANGES);
    }

    @Bean(Y_DEAD_LETTER_EXCHANGE)
    public DirectExchange YExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明队列
    @Bean(QUEUE_A)
    public Queue queueA() {
        HashMap<String, Object> arguments = new HashMap<>();
        //设置死信队列中的exchange
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置私信队列中的routing-key
        arguments.put("x-dead-letter-routing-key", Q_TO_QD_RK);
        //设置队列中的消息TTL为10S
        arguments.put("x-message-ttl", 10000);

        return QueueBuilder.nonDurable(QUEUE_A).withArguments(arguments).build();
    }

    @Bean(QUEUE_B)
    public Queue queueB() {
        HashMap<String, Object> arguments = new HashMap<>();
        //设置死信队列中的exchange
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置私信队列中的routing-key
        arguments.put("x-dead-letter-routing-key", Q_TO_QD_RK);
        //设置队列中的消息TTL为10S
        arguments.put("x-message-ttl", 40000);

        return QueueBuilder.nonDurable(QUEUE_B).withArguments(arguments).build();
    }

    @Bean(QUEUE_C)
    public Queue queueC() {
        HashMap<String, Object> arguments = new HashMap<>();
        //设置死信队列中的exchange
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //设置私信队列中的routing-key
        arguments.put("x-dead-letter-routing-key", Q_TO_QD_RK);

        return QueueBuilder.nonDurable(QUEUE_C).withArguments(arguments).build();
    }

    @Bean(DEAD_LETTER_QUEUE)
    public Queue queueDead() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    //声明绑定信息
    @Bean
    public Binding queueABindX(
            @Qualifier(QUEUE_A) Queue queueA,
            @Qualifier(X_EXCHANGES) DirectExchange xExchange
    ) {
        return BindingBuilder.bind(queueA).to(xExchange).with(X_TO_QA_RK);
    }

    @Bean
    public Binding queueBBindX(
            @Qualifier(QUEUE_B) Queue queueB,
            @Qualifier(X_EXCHANGES) DirectExchange xExchange
    ) {
        return BindingBuilder.bind(queueB).to(xExchange).with(X_TO_QB_RK);
    }

    @Bean
    public Binding queueCBindX(
            @Qualifier(QUEUE_C) Queue queuec,
            @Qualifier(X_EXCHANGES) DirectExchange xExchange
    ) {
        return BindingBuilder.bind(queuec).to(xExchange).with(X_TO_QC_RK);
    }

    @Bean
    public Binding queueDeadBindY(
            @Qualifier(DEAD_LETTER_QUEUE) Queue deadQueue,
            @Qualifier(Y_DEAD_LETTER_EXCHANGE) DirectExchange yExchange
    ) {
        return BindingBuilder.bind(deadQueue).to(yExchange).with(Q_TO_QD_RK);
    }

}
