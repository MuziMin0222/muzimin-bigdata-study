package com.muzimin.exchanges.topic;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:43
 **/
public class Consumer02 {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        String exchangeName = "topic_log";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

        String queueName = "Q2";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, "*.*.rabbit");

        channel.basicConsume(
                queueName,
                true, (consumerTag, message) -> System.out.println(message.getEnvelope().getRoutingKey() + "---" + new String(message.getBody())),
                s -> {
                });
    }
}
