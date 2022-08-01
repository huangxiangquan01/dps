package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import cn.xqhuang.dps.service.DemoService;
import cn.xqhuang.dps.service.HelloService;
import cn.xqhuang.dps.service.TimeoutService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/915:54
 */
public class StubDubboConsumerDemoTest extends DubboConsumerTest {

    //    '@Reference(version = "default", timeout = 1000, stub = "cn.xqhuang.dps.servic.DemoServiceStub")
    @Reference(version = "default", timeout = 1000, stub = "true")
    private DemoService demoService;

    @Test
    public void test() {
        System.out.println((demoService.sayHello("周瑜")));
    }
}