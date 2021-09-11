package com.atguigu.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AckListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(deliveryTag);
            System.out.println(new String(message.getBody()));
            System.out.println("logiccccccc");
//            int i = 10 / 0;
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            e.printStackTrace();
            Thread.sleep(10000);
            channel.basicNack(deliveryTag,true,true);
        }
    }
//    @Override
//    public void onMessage(Message message) {
//        System.out.println(new String(message.getBody()));
//    }
}
