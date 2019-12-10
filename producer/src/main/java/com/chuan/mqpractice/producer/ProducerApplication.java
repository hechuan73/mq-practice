package com.chuan.mqpractice.producer;

import com.chuan.mqpractice.producer.rocketmq.MessagingTest;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;

/**
 * @author hechuan
 */
@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
		SpringApplication.run(ProducerApplication.class, args);
		MessagingTest.sendMsgSync();
	}

}
