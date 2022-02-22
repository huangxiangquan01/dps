package cn.xqhuang.dps.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder.directExchange("test_exchange").build();
    }

    @Bean
    public Queue getQueue() {
        return QueueBuilder.durable("test_queue").build();
    }

    @Bean
    public Binding binding(@Qualifier("getExchange") Exchange exchange,
                           @Qualifier("getQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with("info").noargs();
    }
}
