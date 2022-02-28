package cn.xqhuang.dps.rocket.controller;

import cn.xqhuang.dps.model.BaseResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RocketController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping(value = "/send")
    public BaseResult<String> sendMsg(String msg) {
        rocketMQTemplate.convertAndSend("product-topic-1", msg);
        return new BaseResult.Builder<String>()
                .code(BaseResult.SUCCESS)
                .success("success")
                .build();
    }
}
