package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import cn.xqhuang.dps.service.DemoService;
import cn.xqhuang.dps.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author huangxq
 * @description: 负载均衡
 *              RandomLoadBalance	加权随机	默认算法，默认权重相同
 *              RoundRobinLoadBalance	加权轮询	借鉴于 Nginx 的平滑加权轮询算法，默认权重相同，
 *              LeastActiveLoadBalance	最少活跃优先 + 加权随机	背后是能者多劳的思想
 *              ShortestResponseLoadBalance	最短响应优先 + 加权随机	更加关注响应速度
 *              ConsistentHashLoadBalance	一致性 Hash	确定的入参，确定的提供者，适用于有状态请求
 *
 * @date 2022/5/915:52
 */
public class LoadBalanceDubboConsumerDemoTest extends DubboConsumerTest {

    @Reference(version = "default", loadbalance = "roundrobin")
    private DemoService demoService;

    @Test
    public void test() {
        // 用来负载均衡
//        for (int i = 0; i < 1000; i++) {
//            System.out.println((demoService.sayHello("周瑜")));
//            try {
//                Thread.sleep(1 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        // 一致性hash算法测试
        for (int i = 0; i < 1000; i++) {
            System.out.println((demoService.sayHello(i%5+"周瑜")));
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}