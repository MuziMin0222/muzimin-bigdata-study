package com.muzimin.workerqueue;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

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

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", "hello", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(message + "消息发送成功！");
        }
    }
}
