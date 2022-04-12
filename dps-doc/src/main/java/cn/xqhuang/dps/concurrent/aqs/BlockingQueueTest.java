package cn.xqhuang.dps.concurrent.aqs;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/4/1119:07
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            new Thread(() -> {
                try {
                   Thread.sleep(10000);
                } catch (Exception e) {

                } finally {
                    countDownLatch.countDown();

                    System.out.println("countDownLatch.countDown();");
                }
            }).start();
            countDownLatch.await();
        } catch (Exception e) {

        } finally {
            System.out.println("countDownLatch.await();");
        }


    }
}
