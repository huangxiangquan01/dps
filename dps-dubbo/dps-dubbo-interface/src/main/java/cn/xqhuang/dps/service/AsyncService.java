package cn.xqhuang.dps.service;

import java.util.concurrent.CompletableFuture;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/5/1014:24
 */
public interface AsyncService {

    String sayHello(String name);

    CompletableFuture<String> sayHelloAsync(String name);
}
