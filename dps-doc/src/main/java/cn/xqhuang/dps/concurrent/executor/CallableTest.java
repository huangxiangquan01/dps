package cn.xqhuang.dps.concurrent.executor;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/4/1212:43
 */
public class CallableTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callable = () -> "这是一个返回1" + Thread.currentThread().getName();

        try {
            String str = executorService.submit(callable).get();

            System.out.println(str);
        } catch (Exception e) {

        }
        System.out.println("main" + Thread.currentThread().getName());
    }
}
