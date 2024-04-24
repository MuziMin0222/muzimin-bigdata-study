package com.muzimin.consumer;

import com.muzimin.config.ConfirmConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: 李煌民
 * @date: 2024-04-24 21:30
 **/
@Component
@Slf4j
public class ConfirmConsumerListener {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    void confirmConsumer(Message message, Channel channel) {
        log.info("收到队列{}的消息{}", ConfirmConfig.CONFIRM_QUEUE, new String(message.getBody()));
    }

    @RabbitListener(queues = {ConfirmConfig.WARN_QUEUE, ConfirmConfig.BACKUP_QUEUE})
    void backupConsumer(Message message, Channel channel) {
        log.info("收到报警队列和备份队列{}的消息{}", ConfirmConfig.CONFIRM_QUEUE, new String(message.getBody()));
    }
}
