package cn.xqhuang.dps.spring.proxy.impl;

import cn.xqhuang.dps.spring.proxy.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public void add(String name) {
        System.out.println("save success");
    }

    @Override
    public void add2(String name) {
        System.out.println("save success2");
    }
}
