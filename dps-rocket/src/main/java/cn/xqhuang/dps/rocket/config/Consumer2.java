package cn.xqhuang.dps.rocket.config;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "product-topic-1", consumerGroup = "consumer-1")
public class Consumer2 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("Receive message_2："+ message);
    }
}
