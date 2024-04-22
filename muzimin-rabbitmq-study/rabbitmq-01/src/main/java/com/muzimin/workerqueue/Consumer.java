package com.muzimin.workerqueue;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 李煌民
 * @date: 2024-04-19 16:13
 **/
public class Consumer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        channel.basicConsume(
                "hello",
                true,
                (s, delivery) -> System.out.println(Thread.currentThread().getName() + "接收到的消息：" + new String(delivery.getBody())),
                s -> System.out.println("消息被中断消费"));
    }
}
