package com.muzimin.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 李煌民
 * @date: 2024-04-24 21:21
 **/
@Configuration
public class ConfirmConfig {
    public static final String CONFIRM_EXCHANGE = "confirm.exchange";
    public static final String CONFIRM_QUEUE = "confirm.queue";
    public static final String CONFIRM_ROUTING_KEY = "confirm-rounting-key";

    public static final String BACKUP_EXCHANGE = "backup.exchange";
    public static final String BACKUP_QUEUE = "backup.queue";
    public static final String WARN_QUEUE = "warn.queue";

    @Bean(CONFIRM_EXCHANGE)
    DirectExchange confirmExchange() {
        return ExchangeBuilder
                .directExchange(CONFIRM_EXCHANGE)
                .durable(true)
                .withArgument("alternate-exchange", BACKUP_EXCHANGE)
                .build();
    }

    @Bean(BACKUP_EXCHANGE)
    FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean(BACKUP_QUEUE)
    Queue backupQueue() {
        return new Queue(BACKUP_QUEUE);
    }

    @Bean(WARN_QUEUE)
    Queue warnQueue() {
        return new Queue(WARN_QUEUE);
    }

    @Bean(CONFIRM_QUEUE)
    Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    Binding confirmBingding(
            @Qualifier(CONFIRM_QUEUE) Queue confirmQueue,
            @Qualifier(CONFIRM_EXCHANGE) DirectExchange confirmExchange
    ) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    Binding backupBinding(@Qualifier(BACKUP_QUEUE) Queue backupQueue,
                          @Qualifier(BACKUP_EXCHANGE) FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    Binding warnBinding(@Qualifier(WARN_QUEUE) Queue warnQueue,
                        @Qualifier(BACKUP_EXCHANGE) FanoutExchange backupExchange) {
        return BindingBuilder.bind(warnQueue).to(backupExchange);
    }
}
