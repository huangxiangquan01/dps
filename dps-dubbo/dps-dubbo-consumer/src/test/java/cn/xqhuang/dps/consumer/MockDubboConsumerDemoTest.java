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
 * @description: Mock
 * @date 2022/5/915:53
 */
public class MockDubboConsumerDemoTest extends DubboConsumerTest {

    // @Reference(version = "timeout", timeout = 1000, mock = "force: return 123")
    @Reference(version = "default", timeout = 100, mock = "true")
    private DemoService demoService;

    @Test
    public void test() {
        System.out.println((demoService.sayHello("周瑜")));
    }
}