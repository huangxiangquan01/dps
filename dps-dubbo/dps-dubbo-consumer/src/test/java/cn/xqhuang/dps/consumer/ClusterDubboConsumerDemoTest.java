package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import cn.xqhuang.dps.service.DemoService;
import cn.xqhuang.dps.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author huangxq
 * @description: 集群容错
 * @date 2022/5/915:40
 */
public class ClusterDubboConsumerDemoTest extends DubboConsumerTest {

    /**
     * Failfast Cluster
     * 快速失败，只发起一次调用，失败立即报错。通常用于非幂等性的写操作，比如新增记录
     *
     * Failsafe Cluster
     * 失败安全，出现异常时，直接忽略。通常用于写入审计日志等操作。
     *
     * Failback Cluster
     * 失败自动恢复，后台记录失败请求，定时重发。通常用于消息通知操作。
     *
     * Forking Cluster
     * 并行调用多个服务器，只要一个成功即返回。通常用于实时性要求较高的读操作，但需要浪费更多服务资源。可通过 forks="2" 来设置最大并行数。
     *
     * Broadcast Cluster
     * 广播调用所有提供者，逐个调用，任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息。
     */
    @Reference(version = "default", timeout = 1000, cluster = "failfast")
    private DemoService demoService;

    @Test
    public void test() {
        System.out.println(demoService.sayHello("周瑜"));
    }
}