package com.jeesuite.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jeesuite.kafka.message.DefaultMessage;
import com.jeesuite.kafka.spring.TopicProducerSpringProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-kafka-producer.xml")
public class ProducerSimpleClient implements ApplicationContextAware{
	
	@Autowired
	private TopicProducerSpringProvider topicProducer;
	
	@Test
	public void testPublish() throws InterruptedException{
        //默认模式（异步/ ）发送
		topicProducer.publish("demo-topic", new DefaultMessage("hello,man"));
		
		DefaultMessage msg = new DefaultMessage("hello,man")
		            .header("headerkey1", "headerval1")//写入header信息
		            .header("headerkey1", "headerval1")//写入header信息
		            .partitionFactor(1000) //分区因子，譬如userId＝1000的将发送到同一分区、从而发送到消费者的同一节点(有状态)
		            .consumerAck(true);// 已消费回执(未发布)
		
		//异步发送
		topicProducer.publish("demo-topic", msg,true);
				
	}
	
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {}

}
