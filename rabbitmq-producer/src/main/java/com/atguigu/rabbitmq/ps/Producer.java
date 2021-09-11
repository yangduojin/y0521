package com.atguigu.rabbitmq.ps;

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

        String exchangeName = "test_fanout";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT,true,false,false,null);

        String queueName1 = "test_fanout_queue1";
        String queueName2 = "test_fanout_queue2";
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);

        channel.queueBind(queueName1,exchangeName,"");
        channel.queueBind(queueName2,exchangeName,"");

        String message = "hello,world good morning  " ;

        for (int i = 0; i < 10; i++) {
        channel.basicPublish(exchangeName,"",null,message.getBytes());
        }

        channel.close();
        connection.close();
    }
}
