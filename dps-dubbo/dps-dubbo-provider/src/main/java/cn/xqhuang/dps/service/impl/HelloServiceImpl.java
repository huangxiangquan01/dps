package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return String.format("%s, hello", name);
    }
}
