package com.muzimin.exchanges.fanout;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2024-04-23 14:00
 * fanout类型的交换机测试
 **/
public class Consumer01 {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();
        //定义一个fanout交换器
        String exchangeName = "logs";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
        //定义一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //队列与交换机进行绑定，routingKey的名称是固定的
        channel.queueBind(queueName, exchangeName, "");

        channel.basicConsume(
                queueName,
                true,
                (consumerTag, message) -> System.out.println(Consumer01.class.getName() + ":" + new String(message.getBody())),
                s -> {
                });

    }
}
