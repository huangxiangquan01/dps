package cn.xqhuang.dps.spring;

import org.springframework.stereotype.Component;

@Component
public class Cat {

    public Cat() {
        System.out.println("初始化 ------> Student");
    }

    public void test() {
        System.out.println("Cat.text");
    }
}
