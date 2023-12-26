package cn.xqhuang.dps.concurrent.aqs;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    /**
     *
     * @author huangxiangquan@yintatech.com
     * @date 2023/12/26 10:18
     * @param args
        apply 有入参和返回值，入参为前置任务的输出
        accept 有入参无返回值，会返回 CompletableFuture
        run 没有入参也没有返回值，同样会返回 CompletableFuture
        combine 形成一个复合的结构，连接两个 CompletableFuture，并将它们的2个输出结果，作为 combine 的输入
        compose 将嵌套的 CompletableFuture 平铺开，用来串联两个 CompletableFuture
     */
	public static void main(String[] args) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> "test");
        String result = future.join();

        System.out.println(result);


        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 10)
                .thenApplyAsync((e) -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return e * 10;
                }).thenApplyAsync(e -> e - 1)
                //when 的意思，就是任务完成时候的回调。比如我们上面的例子，打算在完成任务后，输出一个 done。它也是属于只有入参没有出参的范畴，适合放在最后一步进行观测
                .whenComplete((r, e) -> System.out.println("done"))
                // exceptionally 从名字就可以看出，是专门处理这种情况的。比如，我们强制某个步骤除以 0，发生异常，捕获后返回 -1，它将能够继续运行。
                .exceptionally(ex->{
                    System.out.println(ex);
                    return -1;
                });

        cf.join();
        System.out.println(cf.get());

        //常用的，还有三个函数：
        //
        //thenAcceptBoth：处理两个任务的情况，有两个任务结果入参，无返回值
        //thenCombine：处理两个任务的情况，有入参有返回值，最喜欢
        //runAfterBoth：处理两个任务的情况，无入参，无返回值
	}
}
