package cn.xqhuang.dps.consumer;

import cn.xqhuang.dps.service.AsyncService;
import cn.xqhuang.dps.consumer.base.DubboConsumerTest;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author huangxq
 * @description: 异步调用  在 Dubbo 中发起异步调用
 *               异步执行   Dubbo 服务提供方的异步执行
 * @date 2022/5/915:35
 */

public class AsyncDubboConsumerTest extends DubboConsumerTest {

    @Reference(version = "async", methods = {@Method(name = "sayHelloAsync", async = true, timeout = 2000)})
    private AsyncService asyncService;

    @Test
    public void sayHello() {
        System.out.println(asyncService.sayHello("向权"));
    }

    @Test
    public void sayHelloAsync() {
        // 调用直接返回CompletableFuture
        CompletableFuture<String> future = asyncService.sayHelloAsync("异步调用");

        // 增加回调
        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println("Response: " + v);
            }
        });

        // 早于结果输出
        System.out.println("Executed before response return.");

        //
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}