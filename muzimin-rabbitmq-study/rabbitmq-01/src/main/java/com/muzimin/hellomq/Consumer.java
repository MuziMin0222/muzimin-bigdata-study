package com.muzimin.hellomq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: 李煌民
 * @date: 2024-04-19 15:41
 **/
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("root");
        factory.setPassword("123456");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        /**
         * basicConsume接收队列中的消息
         *      参数1：指定消费的队列名称
         *      参数2：消费后是否要自动应答（autoAck），true表示自动应答，false表示手动应答
         *      参数3：当消息接收后的回调函数
         *      参数4：当消息被中断消费的回调函数
         */
        channel.basicConsume(
                "hello",
                true,
                (s, delivery) -> System.out.println("接收到的消息：" + new String(delivery.getBody())),
                s -> System.out.println("消息被中断消费"));
    }
}
