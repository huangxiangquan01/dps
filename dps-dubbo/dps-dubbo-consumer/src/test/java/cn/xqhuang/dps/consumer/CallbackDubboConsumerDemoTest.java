package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import cn.xqhuang.dps.service.CallBackService;
import cn.xqhuang.dps.service.DemoService;
import cn.xqhuang.dps.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/915:39
 */
public class CallbackDubboConsumerDemoTest extends DubboConsumerTest {

    @Reference(version = "callback")
    private CallBackService callBackService;

    @Test
    public void test() {
        // 用来进行callback
        System.out.println(callBackService.sayHello("xqhuang", "d1", new DemoServiceListenerImpl()));
        System.out.println(callBackService.sayHello("xqhuang", "d2", new DemoServiceListenerImpl()));
        System.out.println(callBackService.sayHello("xqhuang", "d3", new DemoServiceListenerImpl()));
    }
}