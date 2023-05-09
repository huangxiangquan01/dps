package cn.xqhuang.dps.controller;

import cn.xqhuang.dps.framework.ProxyFactory;
import cn.xqhuang.dps.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/consumer/user/say")
    public String sayHello() {
        HelloService service = ProxyFactory.getProxy(HelloService.class);
        return service.sayHello("sam");
    }
}
