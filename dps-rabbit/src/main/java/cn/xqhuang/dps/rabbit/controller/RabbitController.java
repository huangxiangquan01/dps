package cn.xqhuang.dps.rabbit.controller;

import cn.xqhuang.dps.model.BaseResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/send")
    public BaseResult<String> sendMsg(String msg) {
        rabbitTemplate.convertAndSend("test_exchange","info", msg);
        return new BaseResult.Builder<String>()
                .code(BaseResult.SUCCESS)
                .success("success")
                .build();
    }
}
