package com.muzimin.exchanges.fanout;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:18
 **/
public class Producer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();
        Scanner scanner = new Scanner(System.in);

        String exchangeName = "logs";

        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(exchangeName, "", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
