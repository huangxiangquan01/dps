package cn.xqhuang.dps.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = {"test_queue"})
    public void listening(Message message){
        System.out.println(message.getBody());
    }
}
