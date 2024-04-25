package com.muzimin.priority_queue;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: 李煌民
 * @date: 2024-04-19 16:21
 **/
public class Producer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        AMQP.BasicProperties build = new AMQP.BasicProperties().builder().priority(5).build();

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                //当I=5时，设置优先级
                channel.basicPublish(
                        "",
                        "hello",
                        build,
                        (i + ":info").getBytes(StandardCharsets.UTF_8)
                );
            } else {
                channel.basicPublish(
                        "",
                        "hello",
                        null,
                        (i + ":info").getBytes(StandardCharsets.UTF_8)
                );
            }
        }
    }
}
