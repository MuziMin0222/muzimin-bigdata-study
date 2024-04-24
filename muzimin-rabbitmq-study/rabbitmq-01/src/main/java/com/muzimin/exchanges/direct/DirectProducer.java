package com.muzimin.exchanges.direct;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:31
 **/
public class DirectProducer {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        String exchangeName = "direct_logs";

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String next = sc.next();
            String[] split = next.split(":");
            channel.basicPublish(exchangeName, split[0], null, split[1].getBytes(StandardCharsets.UTF_8));
        }
    }
}
