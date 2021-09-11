package com.atguigu.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("simple_queue",true,false,false,null);

        for (int i = 0; i < 10; i++) {
            String message = "hello,world good morning  " + i;
            channel.basicPublish("","simple_queue",null,message.getBytes());
        }
        channel.close();
        connection.close();
    }
}
