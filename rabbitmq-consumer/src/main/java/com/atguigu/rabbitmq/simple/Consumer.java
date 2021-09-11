package com.atguigu.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("simple_queue",true,false,false,null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                System.out.println("consumerTag  is   "+ consumerTag);
                System.out.println("exchange  is   "+ envelope.getExchange());
                System.out.println("routingKey  is   "+ envelope.getRoutingKey());
                System.out.println("properties  is   "+ properties);
                System.out.println("body  is   "+ new String(body));
            }
        };
        channel.basicConsume("simple_queue",true,consumer);
    }
}
