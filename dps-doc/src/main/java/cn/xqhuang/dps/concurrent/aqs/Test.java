package cn.xqhuang.dps.concurrent.aqs;

import java.util.concurrent.CompletableFuture;

public class Test {

    public static void main(String[] args) {
        test3();
    }

    private static void test1() {
        CompletableFuture<String> task = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1开始执行，当前时间：" + System.currentTimeMillis());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1执行完毕，当前时间：" + System.currentTimeMillis());
            return "task1";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2开始执行，当前时间：" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2执行完毕，当前时间：" + System.currentTimeMillis());
            return "task2";
        });

        task.acceptEitherAsync(task2, (res) -> {
            System.out.println("任务3开始执行，当前时间：" + System.currentTimeMillis());
            System.out.println("上一个任务的结果为：" + res);
        });

// 增加一些延迟时间，确保异步任务有足够的时间完成
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test2() {
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1开始执行，当前时间：" + System.currentTimeMillis());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1执行完毕，当前时间：" + System.currentTimeMillis());
            return "task1";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务2开始执行，当前时间：" + System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务2执行完毕，当前时间：" + System.currentTimeMillis());
            return "task2";
        });

        CompletableFuture.allOf(task1, task2).join();

    }

    private static void test3(){
        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> "hello!")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world!"));
        try {
            System.out.println(future.get());
        } catch (Exception e ) {

        }
    }
}
