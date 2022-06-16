package org.example;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "demo-topic", consumerGroup = "${rocketmq.consumer.group}",
        messageModel = MessageModel.CLUSTERING, consumeMode = ConsumeMode.ORDERLY,
        selectorExpression = "type='store' or a < 7", selectorType = SelectorType.SQL92
        /*selectorExpression = "tag1", selectorType = SelectorType.TAG*/)
public class ConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }
}
