package com.muzimin.message_confirm;

import com.muzimin.utils.FactoryUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author: 李煌民
 * @date: 2024-04-23 10:32
 **/
public class AsyncMessageConfirm {
    public static void main(String[] args) throws IOException {
        Channel channel = FactoryUtils.getChannel();
        channel.confirmSelect();
        String queue = UUID.randomUUID().toString();
        channel.queueDeclare(queue, true, false, false, null);

        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        /** 添加一个异步确认的监听器
         * 参数1，确认收到消息的回调函数
         * 参数2，没有收到消息的回调函数
         */
        channel.addConfirmListener(
                new ConfirmCallback() {
                    @Override
                    public void handle(long deliveryTag, boolean multiple) throws IOException {
                        if (multiple) {
                            //如果是批量确认的，那么就将当前序号之后的所有消息进行弹出，可以确认小于等于当前序列号的消息
                            ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag, true);
                            confirmed.clear();
                        } else {
                            //如果不是批量确认, 只清除当前序号的消息，确认当前序列号消息
                            outstandingConfirms.remove(deliveryTag);
                        }
                    }
                }, null);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = i + "";
            /**
             * channel.getNextPublishSeqNo(): 获取下一个消息的序列号
             * 通过序列号与消息进行绑定，消息认为都是未确认的
             */
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
            channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
        }
        long end = System.currentTimeMillis();

        //异步发布确认耗时：20ms
        System.out.println("异步发布确认耗时：" + (end - start) + "ms");
    }
}
