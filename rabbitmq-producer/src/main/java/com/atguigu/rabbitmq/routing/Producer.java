package com.atguigu.rabbitmq.routing;

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

        String exchangeName = "test_direct";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT,true,false,false,null);

        String queueName1 = "test_direct_queue1";
        String queueName2 = "test_direct_queue2";
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);

        channel.queueBind(queueName1,exchangeName,"error");
        channel.queueBind(queueName2,exchangeName,"error");
        channel.queueBind(queueName2,exchangeName,"info");

        String message = "hello,world good morning  " ;

        channel.basicPublish(exchangeName,"info",null,message.getBytes());

        channel.close();
        connection.close();
    }
}
