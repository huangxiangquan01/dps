package cn.xqhuang.dps.service;


import cn.xqhuang.dps.service.DemoService;

public class DemoServiceMock implements DemoService {

    @Override
    public String sayHello(String name) {
        return "出现Rpc异常，进行了mock";
    }
}
