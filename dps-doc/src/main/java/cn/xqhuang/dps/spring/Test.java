package cn.xqhuang.dps.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Cat.class);
        Cat cat = applicationContext.getBean(Cat.class);
        cat.test();
    }
}
