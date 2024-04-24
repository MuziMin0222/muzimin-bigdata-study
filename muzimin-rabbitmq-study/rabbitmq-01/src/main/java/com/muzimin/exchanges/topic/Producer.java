package com.muzimin.exchanges.topic;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:47
 **/
public class Producer {
    public static void main(String[] args) {
        Channel channel = FactoryUtils.getChannel();

        String exchangeName = "topic_log";
        HashMap<String, String> map = new HashMap<>();

        map.put("quick.orange.rabbit", "被队列 Q1Q2 接收到");
        map.put("lazy.orange.elephant", "被队列 Q1Q2 接收到");
        map.put("quick.orange.fox", "被队列 Q1 接收到");
        map.put("lazy.brown.fox", "被队列 Q2 接收到");
        map.put("lazy.pink.rabbit", "虽然满足两个绑定但只被队列 Q2 接收一次");
        map.put("quick.brown.fox", "不匹配任何绑定不会被任何队列接收到会被丢弃");
        map.put("quick.orange.male.rabbit", "是四个单词不匹配任何绑定会被丢弃");
        map.put("lazy.orange.male.rabbit", "是四个单词但匹配 Q2");

        map.forEach((s, s2) -> {
            try {
                channel.basicPublish(exchangeName, s, null, s2.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
