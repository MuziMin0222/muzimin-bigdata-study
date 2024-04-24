package com.muzimin.dead_queue.reject;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

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

        for (int i = 0; i < 10; i++) {
            channel.basicPublish(normalExchange, normalRoutingKey, null, (i + "data").getBytes(StandardCharsets.UTF_8));
        }
    }
}
