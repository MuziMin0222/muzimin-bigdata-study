package com.muzimin.hellomq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: 李煌民
 * @date: 2024-04-19 14:58
 **/
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("root");
        factory.setPassword("123456");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /**
         * queueDeclare生成一个队列
         *      参数1：设置的队列名称
         *      参数2：队列里面的消息是否持久化，默认是存储在内存中
         *      参数3：该队列是否只提供一个消费者进行消费，是否进行共享；true则表示可以多个消费者共享
         *      参数4：是否自动删除，最后一个消费者断开链接之后，该队列是否自动删除；true表示自动删除
         *      参数5：其他参数
         */
        channel.queueDeclare("hello", false, false, false, null);

        /**
         * basicPublish发送一个消息
         *      参数1：发送到哪一个交换机
         *      参数2：路由的key是哪个，指的是路由的名称
         *      参数3：其他参数消息
         *      参数4：发送消息的消息体
         */
        channel.basicPublish("", "hello", null, "hello rabbit mq".getBytes(StandardCharsets.UTF_8));

        System.out.println("消费发送完成");
    }
}
