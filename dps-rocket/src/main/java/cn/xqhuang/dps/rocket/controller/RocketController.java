package cn.xqhuang.dps.rocket.controller;

import cn.xqhuang.dps.model.BaseResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RocketController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping(value = "/send")
    public BaseResult<String> sendMsg() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.convertAndSend("product-topic-1", UUID.randomUUID() + "___________" + i);
        }
        return new BaseResult.Builder<String>()
                .code(BaseResult.SUCCESS)
                .success("success")
                .build();
    }
}
