package cn.xqhuang.dps.service;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/915:39
 */
public interface CallBackService  {

    // 添加回调
    default String sayHello(String name, String key, DemoServiceListener listener) {
        return null;
    }
}