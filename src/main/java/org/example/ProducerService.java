package org.example;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProducerService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage() {
        rocketMQTemplate.convertAndSend("demo-topic", "hello world");
    }

    public void sendSyncMessage() {
        // 发送同步消息，等待应答
        SendResult hello_world = rocketMQTemplate.syncSend("demo-topic", "hello world");
        System.out.println(hello_world);
    }

    public void sendAsyncMessage() {
        // 发送异步消息
        rocketMQTemplate.asyncSend("demo-topic", "hello world", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
            }
        });
    }

    public void sendOneWayMessage() {
        //无关紧要消息
        rocketMQTemplate.sendOneWay("demo-topic", "hello world");
    }

    public void sendOrderlyMessage() {
        // 顺序消息
        rocketMQTemplate.syncSendOrderly("demo-topic", "hello world", "11111111111111111");
    }

    public void sendDelayMessage() {
        // 延迟消息
        rocketMQTemplate.syncSend("demo-topic", MessageBuilder.withPayload("hello world").build(), 3000, 1);
    }

    public void sendMessageWithTag() {
        // 带tag消息
        rocketMQTemplate.convertAndSend("demo-topic:tag1", "hello -world");
    }

    public void sendMessageWithSQL() {
        Message msg = MessageBuilder.withPayload("hello world").build();
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("type", "store");
        headers.put("a", 4);
        rocketMQTemplate.convertAndSend("demo-topic", msg, headers);

    }

    public void sendTransactionMessage() {
        rocketMQTemplate.sendMessageInTransaction("demo-topic", MessageBuilder.withPayload("hello world").build(), null);
    }

    @RocketMQTransactionListener
    class TransactionListenerImpl implements RocketMQLocalTransactionListener {

        @Override
        public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
            return RocketMQLocalTransactionState.UNKNOWN;
        }

        @Override
        public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
            return RocketMQLocalTransactionState.COMMIT;
        }
    }


}
