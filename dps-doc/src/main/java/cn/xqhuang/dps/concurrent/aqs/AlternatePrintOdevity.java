package cn.xqhuang.dps.concurrent.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author huangxq
 * @description: TODO
 * @date 2023/2/1012:56
 */
public class AlternatePrintOdevity {



    public static void main(String[] args) {
        // final Object lockObj = new Object();
        CyclicBarrier countDownLatch = new CyclicBarrier(1);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 4; i++) {
                        System.out.println(i);
                        countDownLatch.reset();
                        try {
                            countDownLatch.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }

            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    char c = (char) ('a' + i);
                        System.out.println(c);
                        countDownLatch.reset();
                        try {
                            countDownLatch.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }

            }
        });

        thread1.start();
        thread2.start();

    }
}
