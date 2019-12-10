package com.chuan.mqpractice.producer.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author hechuan
 */
public class MessagingTest {

    /**
     * Send message synchronously.
     *
     * @throws UnsupportedEncodingException If the named charset is not supported.
     * @throws MQClientException If there is any unexpected error.
     * @throws RemotingException If there is any network-tier error.
     * @throws InterruptedException if the sending thread is interrupted.
     */
    public static void sendMsgSync() throws MQClientException, UnsupportedEncodingException, RemotingException,
            InterruptedException, MQBrokerException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_1");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        producer.setVipChannelEnabled(false);
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("Topic1", "Tag1",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, 10000);
            System.out.printf("%s%n", sendResult);
        }

        //Shut down once the producer instance is not longer in use.
        //producer.shutdown();
    }

    /**
     * Send message asynchronously.
     *
     * @throws UnsupportedEncodingException If the named charset is not supported.
     * @throws MQClientException If there is any unexpected error.
     * @throws RemotingException If there is any network-tier error.
     * @throws InterruptedException if the sending thread is interrupted.
     */
    public static void sendMsgAsync() throws MQClientException, UnsupportedEncodingException, RemotingException,
            InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_1");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("Topic1", "Tag1", "Key1",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            final int index = i;
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }

    /**
     * One-way transmission is used for cases requiring moderate reliability, such as log collection.
     *
     * @throws UnsupportedEncodingException If the named charset is not supported.
     * @throws MQClientException If there is any unexpected error.
     * @throws RemotingException If there is any network-tier error.
     */
    public static void sendMsgOneWayMode() throws UnsupportedEncodingException, MQClientException, RemotingException,
            InterruptedException {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_1");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 100; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("Topic1", "Tag1",
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }

        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
