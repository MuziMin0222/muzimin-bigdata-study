package com.muzimin.message_confirm;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author: 李煌民
 * @date: 2024-04-22 15:50
 * 单个确认发布
 **/
public class SingleMessageConfirm {
    public static void main(String[] args) throws IOException, InterruptedException {
        Channel channel = FactoryUtils.getChannel();

        //开启发布确定
        channel.confirmSelect();

        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            channel.basicPublish("", queueName, null, (i + "").getBytes(StandardCharsets.UTF_8));
            //服务端返回false或者超时时间内返回，生产者消息会重发
            if (channel.waitForConfirms()) {
                System.out.println("消息发送成功");
            }
        }
        long endTime = System.currentTimeMillis();

        // 479 MS
        System.out.println("程序耗时：" + (endTime - startTime) + "ms");

    }
}
