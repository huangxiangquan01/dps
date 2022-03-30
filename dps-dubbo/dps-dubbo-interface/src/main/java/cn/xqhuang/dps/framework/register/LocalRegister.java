package cn.xqhuang.dps.framework.register;

import java.util.concurrent.ConcurrentHashMap;

public class LocalRegister {

    static ConcurrentHashMap<String, Class> concurrentHashMap = new ConcurrentHashMap<>();

    public static void register(String key, Class<?> clazz) {
        concurrentHashMap.put(key, clazz);
    }

    public static Class getClass(String key) {
        return concurrentHashMap.get(key);
    }
}
