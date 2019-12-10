package com.chuan.mqpractice.consumer;

import com.chuan.mqpractice.consumer.rocketmq.MessagingTest;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hechuan
 */
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) throws MQClientException {
        SpringApplication.run(ConsumerApplication.class, args);
        MessagingTest.receive();
    }

}
