package com.muzimin.controller;

import com.muzimin.config.TtlQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author: 李煌民
 * @date: 2024-04-24 16:22
 **/
@RestController
@RequestMapping("/ttl")
@Slf4j
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("send/{message}")
    public void sendMsg(@PathVariable("message") String message) {
        log.info("当前时间：{},发送信息:{}", new Date(), message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGES, TtlQueueConfig.X_TO_QA_RK, "消息来自 ttl 为 10S 的队列:" + message);
        rabbitTemplate.convertAndSend(TtlQueueConfig.X_EXCHANGES, TtlQueueConfig.X_TO_QB_RK, "消息来自 ttl 为 40S 的队列:" + message);
    }

    @GetMapping("send/{message}/{ttl}")
    public void sendMsg(@PathVariable("message") String message,
                        @PathVariable("ttl") String ttl) {
        log.info("当前时间：{},TTL = {}, 发送信息:{}", new Date(), ttl, message);
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.X_EXCHANGES,
                TtlQueueConfig.X_TO_QC_RK,
                "消息来自 ttl 为" + ttl + "的队列:" + message,
                message1 -> {
                    message1.getMessageProperties().setExpiration(ttl);
                    return message1;
                }
        );
    }
}
