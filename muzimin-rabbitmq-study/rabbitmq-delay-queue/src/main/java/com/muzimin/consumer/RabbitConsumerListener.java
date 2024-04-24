package com.muzimin.consumer;

import com.muzimin.config.TtlQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 李煌民
 * @date: 2024-04-24 16:40
 **/
@Slf4j
@Component
public class RabbitConsumerListener {
    @RabbitListener(queues = TtlQueueConfig.DEAD_LETTER_QUEUE)
    public void receiveDeadQueue(Message message, Channel channel) {
        log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), new String(message.getBody()));
    }
}
