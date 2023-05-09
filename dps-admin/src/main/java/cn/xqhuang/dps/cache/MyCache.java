package cn.xqhuang.dps.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.annotation.PostConstruct;

/**
 * @author huangxq
 * @description TODO
 * @date 2023/3/2017:30
 */
public class MyCache {

    public static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumWeight(100000)
            .build();

    @PostConstruct
    public void init() {
        cache.put("zs", "33");
    }
}
