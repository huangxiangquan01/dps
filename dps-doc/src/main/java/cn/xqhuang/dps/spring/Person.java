package cn.xqhuang.dps.spring;

import org.springframework.beans.factory.annotation.Autowired;


public class Person {

    @Autowired
    private Student student;

    public Person() {
        System.out.println("初始化 ------> Person");
    }
}
