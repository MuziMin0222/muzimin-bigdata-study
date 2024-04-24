package com.muzimin.exchanges.direct;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:30
 **/
public class WarnConsumer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        String exchangeName = "direct_logs";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();

        String routingKey = "warn";
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.basicConsume(
                queueName,
                true,
                (consumerTag, message) -> System.out.println(routingKey + ":" + new String(message.getBody())),
                s -> {
                });
    }
}
