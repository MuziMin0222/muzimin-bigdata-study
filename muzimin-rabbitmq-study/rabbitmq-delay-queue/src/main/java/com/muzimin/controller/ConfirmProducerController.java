package com.muzimin.controller;

import com.muzimin.callBack.MyCallbackFunction;
import com.muzimin.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author: 李煌民
 * @date: 2024-04-24 21:27
 **/
@RestController
@RequestMapping("/confirm")
@Slf4j
public class ConfirmProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyCallbackFunction myCallbackFunction;

    @PostConstruct
    void init() {
        rabbitTemplate.setConfirmCallback(myCallbackFunction);
        //true：交换机无法路由时，会将该消息直接返回给到生产者
        //false：交换机无法路由时，会将消息直接丢弃
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(myCallbackFunction);
    }

    /**
     * 测试交换机收不到消息的情况
     *
     * @param message
     */
    @GetMapping("test01/{message}")
    void sendMsg(@PathVariable("message") String message) {
        CorrelationData correlationData1 = new CorrelationData("1");
        String message1 = message + 1;
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, ConfirmConfig.CONFIRM_ROUTING_KEY, message1, correlationData1);

        CorrelationData correlationData2 = new CorrelationData("2");
        String message2 = message + 2;
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE + "2", ConfirmConfig.CONFIRM_ROUTING_KEY, message2, correlationData2);
    }

    /**
     * 测试交换机无法路由的场景，调用回退函数将数据发给生产者
     *
     * @param message
     */
    @GetMapping("test02/{message}")
    void sendMes01(@PathVariable("message") String message) {
        CorrelationData correlationData1 = new CorrelationData("1");
        String message1 = message + 1;
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, ConfirmConfig.CONFIRM_ROUTING_KEY, message1, correlationData1);

        CorrelationData correlationData2 = new CorrelationData("2");
        String message2 = message + 2;
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE, ConfirmConfig.CONFIRM_ROUTING_KEY + 2, message2, correlationData2);
    }
}
