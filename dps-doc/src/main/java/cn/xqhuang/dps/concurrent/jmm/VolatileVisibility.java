package cn.xqhuang.dps.concurrent.jmm;

public class VolatileVisibility {

    public static volatile int i = 0;

    public static void increase() {
        i++;
    }

    public static void main(String[] args) throws Exception{
        for (int j = 0; j < 100000; j++) {
            new Thread(() -> VolatileVisibility.increase(), "Thread:" + i).start();
        }

        Thread.sleep(1000);

        System.out.println(i);
    }
}
