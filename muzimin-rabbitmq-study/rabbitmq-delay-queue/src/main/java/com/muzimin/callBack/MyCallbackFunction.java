package com.muzimin.callBack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: 李煌民
 * @date: 2024-04-24 21:40
 **/
@Component
@Slf4j
public class MyCallbackFunction implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    /**
     * 交换机是否收到消息，都会调用该回调函数
     *
     * @param correlationData 消息相关的数据，在发送的时候，自己定义
     * @param ack             交换机是否接收到消息，收到为true，没收到是false
     * @param cause           没有收到消息的具体原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机收到了消息，ID为：{}", id);
        } else {
            log.error("交换机没有收到消息，原因是：{}", cause);
        }
    }

    /**
     * 当交换机不能将数据进行路由时，调用该回调函数
     *
     * @param returned 返回的消息对应的信息
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息:{}被服务器退回，退回原因:{}, 交换机是:{}, 路由 key:{}",
                new String(returned.getMessage().getBody()),
                returned.getReplyText(),
                returned.getExchange(),
                returned.getRoutingKey()
        );
    }
}
