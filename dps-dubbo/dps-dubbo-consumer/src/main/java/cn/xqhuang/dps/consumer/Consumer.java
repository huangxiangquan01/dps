package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.framework.ProxyFactory;
import cn.xqhuang.dps.service.HelloService;

public class Consumer {

    public static void main(String[] args) {
       HelloService service = ProxyFactory.getProxy(HelloService.class);

       System.out.println(service.sayHello("xqhuang"));
    }


}
