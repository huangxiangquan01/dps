package cn.xqhuang.dps.spring;

import org.springframework.beans.factory.annotation.Autowired;


public class Student {

    @Autowired
    private Person person;

    public Student() {
        System.out.println("初始化 ------> Student");
    }
}
