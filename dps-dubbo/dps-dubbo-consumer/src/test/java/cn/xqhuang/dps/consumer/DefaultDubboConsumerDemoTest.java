package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import cn.xqhuang.dps.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/915:39
 */
public class DefaultDubboConsumerDemoTest extends DubboConsumerTest {

    @Reference(version = "default")
    private DemoService demoService;

    @Test
    public void test() {
        System.out.println(demoService.sayHello("向权"));
    }
}