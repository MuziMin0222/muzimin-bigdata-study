package com.muzimin.exchanges.topic;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:43
 **/
public class Consumer01 {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        String exchangeName = "topic_log";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

        String queueName = "Q1";
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, "*.orange.*");

        channel.basicConsume(
                queueName,
                true, (consumerTag, message) -> System.out.println(message.getEnvelope().getRoutingKey() + "---" + new String(message.getBody())),
                s -> {
                });
    }
}
