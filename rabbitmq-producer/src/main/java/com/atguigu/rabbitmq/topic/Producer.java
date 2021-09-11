package com.atguigu.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
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

        String exchangeName = "test_TOPIC";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);

        String queueName1 = "test_TOPIC_queue1";
        String queueName2 = "test_TOPIC_queue2";
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);

        channel.queueBind(queueName1,exchangeName,"*.orange");
        channel.queueBind(queueName2,exchangeName,"*.*");
        channel.queueBind(queueName2,exchangeName,"error.*");

        String message = "hello,world good morning" ;

        channel.basicPublish(exchangeName,"error.error",null,message.getBytes());

        channel.close();
        connection.close();
    }
}
