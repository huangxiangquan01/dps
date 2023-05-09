package cn.xqhuang.dps.concurrent.aqs;

import java.util.concurrent.CompletableFuture;

/**
 * 描述
 *
 * @author xiangquan
 * @date 星期五, 5月 05, 2023, 15:39
 **/
public class CompletableFutureDemo {

    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            return "test";
        });
        String result = future.join();

        System.out.println(result);
        System.out.println("---------------------------------");
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1);

        // Integer join = cf.join();
        try {
            System.out.println(cf.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------");

        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1)
                .whenComplete((r, e)->{
                    System.out.println("done");
                })
                ;
        System.out.println(cf1.join());

        System.out.println("---------------------------------");

        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync(e->e/0)
                .thenApplyAsync(e -> e - 1)
                .exceptionally(ex->{
                    System.out.println(ex);
                    return -1;
                });

        System.out.println(cf2.join());
    }
}
