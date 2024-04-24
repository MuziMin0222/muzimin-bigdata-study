package com.muzimin.dead_queue.ttl;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author: 李煌民
 * @date: 2024-04-24 10:16
 **/
public class Producer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        String normalExchange = "normal_exchange";
        String normalRoutingKey = "normal_routing_key";

        //设置过期时间为10S
        AMQP.BasicProperties build = new AMQP.BasicProperties()
                .builder()
                .expiration("10000")
                .build();

        for (int i = 0; i < 10; i++) {
            channel.basicPublish(normalExchange, normalRoutingKey, build, (i + "data").getBytes(StandardCharsets.UTF_8));
        }
    }
}
