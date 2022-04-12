package cn.xqhuang.dps.concurrent.aqs;

import org.apache.poi.ss.formula.functions.T;
import sun.awt.windows.ThemeReader;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Do {

    public static void main(String[] args) {
        test2();
    }

    public static void test() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                System.out.println();

            }
            System.out.println("执行完成");
        });

        thread.start();
        thread.interrupt();
        System.out.println("第一次调用thread.isInterrupted()：" + thread.isInterrupted());
        System.out.println("第二次调用thread.isInterrupted()：" + thread.isInterrupted());
        //测试当前线程 interrupted（）函数
        System.out.println("第一次调用thread.interrupted()：" + Thread.interrupted());
        System.out.println("第二次调用thread.interrupted()：" + Thread.interrupted());
        System.out.println("thread是否存活：" + thread.isAlive());
    }

    public static void test1() {
        Thread thread = new Thread(() -> {

            LockSupport.park(Thread.currentThread());

            System.out.println("第一次调用thread.interrupted()：" + Thread.interrupted());
            System.out.println("执行完成");
        });

        thread.start();
        thread.interrupt();

        LockSupport.unpark(thread);
    }

    public static void test2() {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        Thread thread = new Thread(() -> {
            try {
                lock.lock();
            } catch (Exception e) {

            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "解锁");
            }
        });
        thread.setName("内部线程1");
        thread.start();

        Thread thread2 = new Thread(() -> {
            try {
                lock.lock();
            } catch (Exception e) {

            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "解锁");
            }
        });
        thread2.setName("内部线程2");
        thread2.start();
        try {
            System.out.println(111111);
            Thread.sleep(1000000);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

}
