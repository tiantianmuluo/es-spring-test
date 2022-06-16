package org.example;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketMqTest {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private ConsumerService consumerService;

    @Test
    public void sendMessage(){
        rocketMQTemplate.convertAndSend("demo-topic","hello world");
    }
}
