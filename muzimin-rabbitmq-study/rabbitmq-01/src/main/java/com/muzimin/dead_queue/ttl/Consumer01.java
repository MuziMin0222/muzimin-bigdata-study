package com.muzimin.dead_queue.ttl;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author: 李煌民
 * @date: 2024-04-24 10:25
 **/
public class Consumer01 {
    public static void main(String[] args) throws IOException {
        String normalExchange = "normal_exchange";
        String normalRoutingKey = "normal_routing_key";
        String normalQueue = "normal_queue";

        String deadExchange = "dead_exchange";
        String deadRoutingKey = "dead_routing_key";
        String deadQueue = "dead_queue";

        Channel channel = FactoryUtils.getChannel();

        channel.exchangeDeclare(normalExchange, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(deadExchange, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(deadQueue, false, false, false, null);
        channel.queueBind(deadQueue, deadExchange, deadRoutingKey);

        //正常队列绑定死信队列
        HashMap<String, Object> arguments = new HashMap<>();
        //正常队列设置死信队列交换机，参数key为固定值
        arguments.put("x-dead-letter-exchange", deadExchange);
        //正常队列设置死信交换机的routing-key
        arguments.put("x-dead-letter-routing-key", deadRoutingKey);
        channel.queueDeclare(normalQueue, false, false, false, arguments);
        channel.queueBind(normalQueue, normalExchange, normalRoutingKey);

        channel.basicConsume(normalQueue,
                true,
                (consumerTag, message) -> {
                    String mes = new String(message.getBody());
                    System.out.println("mes:" + mes);
                },
                s -> {
                });
    }
}
