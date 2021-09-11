package com.atguigu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq.xml")
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testqueue(){
        rabbitTemplate.convertAndSend("spring_queue","queue test");
    }

    @Test
    public void testfanout(){
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "发送到spring_fanout_exchange交换机的广播消息");
    }

    @Test
    public void topicTest(){
        /**
         * 参数1：交换机名称
         * 参数2：路由键名
         * 参数3：发送的消息内容
         */
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj", "发送到spring_topic_exchange交换机atguigu.bj的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj.1", "发送到spring_topic_exchange交换机atguigu.bj.1的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "atguigu.bj.2", "发送到spring_topic_exchange交换机atguigu.bj.2的消息");
        rabbitTemplate.convertAndSend("spring_topic_exchange", "guigu.cn", "发送到spring_topic_exchange交换机guigu.cn的消息");
    }
}
