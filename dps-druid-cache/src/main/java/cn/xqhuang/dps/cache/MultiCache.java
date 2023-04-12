package cn.xqhuang.dps.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Slf4j
public class MultiCache implements Cache {

    private String name;
    private Cache localCache;
    private Cache remoteCache;

    public MultiCache(String name, Cache localCache, Cache remoteCache) {
        this.name = name;
        this.localCache = localCache;
        this.remoteCache = remoteCache;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = localCache.get(key);
        if (valueWrapper == null) {
            valueWrapper = remoteCache.get(key);
            if (valueWrapper != null) {
                localCache.put(key, valueWrapper.get());
            }
        }
        return valueWrapper;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        log.info("从一级缓存取 key ：" + key);
        T value = localCache.get(key, type);
        if (value == null) {
            log.info("从一级缓存取 key ：" + key);
            value = remoteCache.get(key, type);
            if (value != null) {
                localCache.put(key, value);
            }
        }
        return value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper valueWrapper = localCache.get(key);
        if (valueWrapper == null) {
            T value = remoteCache.get(key, valueLoader);
            if (value != null) {
                localCache.put(key, value);
            }
            return value;
        } else {
            return (T) valueWrapper.get();
        }
    }

    @Override
    public void put(Object key, Object value) {
        log.info("写入一级缓存 key ：" + key);
        localCache.put(key, value);
        log.info("写入二级缓存 key ：" + key);
        remoteCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        localCache.putIfAbsent(key, value);
        return remoteCache.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        localCache.evict(key);
        remoteCache.evict(key);
    }

    @Override
    public void clear() {
        localCache.clear();
        remoteCache.clear();
    }
}
