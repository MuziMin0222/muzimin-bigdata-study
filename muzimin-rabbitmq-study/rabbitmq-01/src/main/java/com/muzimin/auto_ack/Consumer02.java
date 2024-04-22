package com.muzimin.auto_ack;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * @author: 李煌民
 * @date: 2024-04-22 08:22
 **/
public class Consumer02 {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();

        channel.basicQos(1);
        channel.basicConsume(
                "auto_ack",
                //取消自动应答
                false,
                (s, delivery) -> {
                    try {
                        //沉睡10S
                        Thread.sleep(1000 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("沉睡10S接收到的消息：" + new String(delivery.getBody()));
                    //参数1：消息标记的Tag; 参数2：false不批量应答，true应答所有消息
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                },
                s -> System.out.println("消息被中断消费"));
    }
}
