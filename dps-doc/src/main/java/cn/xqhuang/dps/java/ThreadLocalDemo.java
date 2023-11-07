package cn.xqhuang.dps.java;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author huangxiangquan@yintatech.com
 * @date 2023/11/7 15:08
 */
public class ThreadLocalDemo {
    private static ExecutorService TTLExecutor = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(5));
    //定义另外一个线程池循环执行，模拟业务场景下多Http请求调用的情况
    private static ExecutorService loopExecutor = Executors.newFixedThreadPool(5);
    private static AtomicInteger i=new AtomicInteger(0);
    //TTL的ThreadLocal
    private static ThreadLocal tl = new TransmittableThreadLocal<>();
    // 这里采用TTL的实现
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new
                ThreadPoolExecutor(1, 1, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(1));
        ThreadLocal<Integer> local = new InheritableThreadLocal<>();
        local.set(1);
        executor.execute(() -> {
            System.out.println("打印1:" + local.get());
        });
        local.set(2);
        System.out.println("打印2:" + local.get());
        executor.execute(() -> {
            System.out.println("打印3:" + local.get());
        });

        //这里采用TTL的实现
        while (true) {
            loopExecutor.execute( () -> {
                if(i.get() < 10){
                    tl.set(i.getAndAdd(1));
                    TTLExecutor.execute(() -> {
                        System.out.println(String.format("子线程名称-%s, 变量值=%s", Thread.currentThread().getName(), tl.get()));
                    });
                }
            });
        }
    }
}
