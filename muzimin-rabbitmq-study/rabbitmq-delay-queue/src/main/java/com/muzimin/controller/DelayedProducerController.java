package com.muzimin.controller;

import com.muzimin.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: 李煌民
 * @date: 2024-04-24 19:22
 **/
@RestController
@RequestMapping("/delayed")
@Slf4j
public class DelayedProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send/{message}/{delayTime}")
    void sendMsg(
            @PathVariable("message") String message,
            @PathVariable("delayTime") Integer delayTime
    ) {
        rabbitTemplate.convertAndSend(
                DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,
                "消息来自 ttl 为" + delayTime + "的延迟队列:" + message,
                message1 -> {
                    message1.getMessageProperties().setDelay(delayTime);
                    return message1;
                });
        log.info("当前时间 ： {},发送一条延迟 {} 毫秒的信息给队列 delayed.queue:{}", new Date(), delayTime, message);
    }
}
