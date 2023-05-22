package cn.xqhuang.dps.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine是基于Java 1.8的高性能本地缓存库，由Guava改进而来，而且在Spring5开始的默认缓存实现就将Caffeine代替原来的Google Guava，
 * 官方说明指出，其缓存命中率已经接近最优值。实际上Caffeine这样的本地缓存和ConcurrentMap很像，即支持并发，
 * 并且支持O(1)时间复杂度的数据存取。二者的主要区别在于：
 * ConcurrentMap将存储所有存入的数据，直到你显式将其移除；
 * Caffeine将通过给定的配置，自动移除“不常用”的数据，以保持内存的合理占用。
 *
 * @author xiangquan
 * @date 星期一, 5月 22, 2023, 10:24
 **/
// @Configuration
public class CaffeineCacheConfig {


    /*
     * Cache手动创建
     */
    @Bean
    public Cache<String, Object> cache() {
        Cache<String, Object> cache = Caffeine.newBuilder()
                //初始数量
                .initialCapacity(10)
                //最大条数
                .maximumSize(10)
                //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
                //最后一次写操作后经过指定时间过期
                .expireAfterWrite(1, TimeUnit.SECONDS)
                //最后一次读或写操作后经过指定时间过期
                .expireAfterAccess(1, TimeUnit.SECONDS)
                //监听缓存被移除
                .removalListener((key, val, removalCause) -> { })
                //记录命中
                .recordStats()
                .build();

        cache.put("1","张三");
        //张三
        System.out.println(cache.getIfPresent("1"));
        //存储的是默认值
        System.out.println(cache.get("2",o -> "默认值"));
        return cache;
    }

    /*
     *  Loading Cache自动创建
     */
    @Bean
    public LoadingCache<String, String> loadingCache() {
        return  Caffeine.newBuilder()
                //创建缓存或者最近一次更新缓存后经过指定时间间隔，刷新缓存；refreshAfterWrite仅支持LoadingCache
                .refreshAfterWrite(10, TimeUnit.SECONDS)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .expireAfterAccess(10, TimeUnit.SECONDS)
                .maximumSize(10)
                //根据key查询数据库里面的值，这里是个lamba表达式
                .build(key -> new Date().toString());
    }

    /*
     * AsyncCache异步获取
     */
    @Bean
    public AsyncLoadingCache<String, String> asyncLoadingCache() {
        AsyncLoadingCache<String, String> asyncLoadingCache = Caffeine.newBuilder()
                //创建缓存或者最近一次更新缓存后经过指定时间间隔刷新缓存；仅支持LoadingCache
                .refreshAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                //根据key查询数据库里面的值
                .buildAsync(key -> {
                    Thread.sleep(1000);
                    return new Date().toString();
                });

        //异步缓存返回的是CompletableFuture
        CompletableFuture<String> future = asyncLoadingCache.get("1");
        future.thenAccept(System.out::println);
        return asyncLoadingCache;
    }
}
