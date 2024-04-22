package com.muzimin.auto_ack;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: 李煌民
 * @date: 2024-04-22 08:22
 **/
public class Producer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        channel.queueDeclare("auto_ack", false, false, false, null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", "auto_ack", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(message + "消息发送成功！");
        }
    }
}
