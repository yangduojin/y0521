package com.atguigu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConfim(){

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                if(b){
                    System.out.println("send message Success :  "+ s);
                }else {
                    System.out.println("send message Fail :    " + s);
                }
            }
        });
        for (int i = 0; i < 10; i++) {
        rabbitTemplate.convertAndSend("test_exchange_confirm","yangxConfim","send message hoooo ");
        }
    }
    
    @Test
    public void testReturn(){
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                System.out.println("message is   " + new String(message.getBody()));
                System.out.println("replyCode is   " + i);
                System.out.println("replyText is   " + s);
                System.out.println("exchange is   " + s1);
                System.out.println("routingKey is   " + s2);
            }
        });
        rabbitTemplate.convertAndSend("test_exchange_confirm","yangxConfim","send message laaaaaaaaaa   ");
    }

    @Test
    public void testTTl(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.abc","ttl TEST");
        }
    }
    
    @Test
    public void testMessage(){
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                return message;
            }
        };
        rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.hehe","Message ttl",messagePostProcessor);
    }

    @Test
    public void testDlx(){
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.#","dlx message");

//        for (int i = 0; i < 10; i++) {
//            rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.#","dlx message");
//        }

    }

    @Test
    public void testOrder() throws Exception{
        rabbitTemplate.convertAndSend("order_exchange","order.msg","hello order ");
        //2.打印倒计时10秒
        for (int i = 10; i > 0 ; i--) {
            System.out.println(i+"...");
            Thread.sleep(1000);
        }
    }
}
