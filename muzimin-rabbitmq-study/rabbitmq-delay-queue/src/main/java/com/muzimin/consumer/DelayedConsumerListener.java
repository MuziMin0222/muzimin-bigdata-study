package com.muzimin.consumer;

import com.muzimin.config.DelayedQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 李煌民
 * @date: 2024-04-24 19:28
 **/
@Component
@Slf4j
public class DelayedConsumerListener {
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    void receiveDelayedQueue(Message message, Channel channel) {
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), new String(message.getBody()));
    }
}
