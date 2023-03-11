package cn.xqhuang.dps.concurrent.aqs;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/2/2712:22
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal();
        threadLocal.set("张三");

        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal();

        inheritableThreadLocal.set("李四");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());
                System.out.println(inheritableThreadLocal.get());

                // System.out.println(Thread.currentThread());
            }
        });

        thread.start();
    }

}
