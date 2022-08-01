package cn.xqhuang.dps.concurrent.executor;


import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/4/1212:43
 */
public class RunnableTest {

    /*public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            System.out.println("这是一个返回1" + Thread.currentThread().getName());

        };

        try {
            Future<String> str = executorService.submit(runnable, "1234");

            String s = str.get();

            System.out.println(s);

        } catch (Exception e) {

        }
        System.out.println("main" + Thread.currentThread().getName());
    }*/

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        list.add("12");
        list.add("123");
        list.add("122");

        new UserDemo().forEachLine(list);
    }

    public static  class UserDemo {

        public void  forEachLine(List<String> list) {
            for (String str : list) {
                get(str);
            }

        }

        public boolean get(String str) {

            if (StringUtils.equals("123", str)) {
                return false;
            }
            System.out.println(str);
            return true;
        }
    }
}
