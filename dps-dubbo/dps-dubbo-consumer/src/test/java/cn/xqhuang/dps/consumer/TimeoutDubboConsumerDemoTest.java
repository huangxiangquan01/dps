package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import cn.xqhuang.dps.service.TimeoutService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/915:55
 */
public class TimeoutDubboConsumerDemoTest extends DubboConsumerTest {


    @Reference(version = "timeout", timeout = 3000)
    private TimeoutService timeoutService;

    @Test
    public void test() {
        // 服务调用超时时间为1秒，默认为3秒
        // 如果这1秒内没有收到服务结果，则会报错
        System.out.println((timeoutService.sayHello("周瑜"))); //xxservestub
    }
}