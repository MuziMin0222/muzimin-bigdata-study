package com.muzimin.message_confirm;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

/**
 * @author: 李煌民
 * @date: 2024-04-23 10:19
 * 批量消息确认
 **/
public class BatchMessageConfirm {
    public static void main(String[] args) throws IOException, InterruptedException {
        Channel channel = FactoryUtils.getChannel();
        channel.confirmSelect();

        String queue = UUID.randomUUID().toString();
        channel.queueDeclare(queue, true, false, false, null);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, (i + "").getBytes(StandardCharsets.UTF_8));
            if (i % 100 == 0) {
                channel.waitForConfirms();
            }
        }
        channel.waitForConfirms();
        long end = System.currentTimeMillis();

        //73ms
        System.out.println("批量确认的耗时时间：" + (end - begin) + "ms");
    }
}
