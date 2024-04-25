package com.muzimin.priority_queue;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author: 李煌民
 * @date: 2024-04-19 16:13
 **/
public class Consumer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        HashMap<String, Object> arguments = new HashMap<>();
        //设置队列的最大优先级 最大可以设置到 255 官网推荐 1-10 如果设置太高比较吃内存和 CPU
        arguments.put("x-max-priority", 10);
        channel.queueDeclare("hello", true, false, false, arguments);

        channel.basicConsume(
                "hello",
                true,
                (s, delivery) -> System.out.println("接收到的消息：" + new String(delivery.getBody())),
                s -> System.out.println("消息被中断消费"));
    }
}
