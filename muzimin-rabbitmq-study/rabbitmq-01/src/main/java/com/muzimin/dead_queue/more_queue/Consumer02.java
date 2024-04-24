package com.muzimin.dead_queue.more_queue;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2024-04-24 10:25
 **/
public class Consumer02 {
    public static void main(String[] args) throws IOException {
        String deadExchange = "dead_exchange";
        String deadRoutingKey = "dead_routing_key";
        String deadQueue = "dead_queue";

        Channel channel = FactoryUtils.getChannel();
        channel.exchangeDeclare(deadExchange, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(deadQueue, false, false, false, null);
        channel.queueBind(deadQueue, deadExchange, deadRoutingKey);

        channel.basicConsume(deadQueue,
                true,
                (consumerTag, message) -> {
                    String mes = new String(message.getBody());
                    System.out.println("mes:" + mes);
                },
                s -> {
                });


    }
}
