package cn.xqhuang.dps.service.impl;

import cn.xqhuang.dps.entity.User;
import cn.xqhuang.dps.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 *
 * @author xiangquan
 * @Description
 * @date 星期一, 5月 22, 2023, 10:45
 **/
@Service
public class UserServiceImpl implements UserService {

    // @Cacheable ：表示该方法支持缓存。当调用被注解的方法时，如果对应的键已经存在缓存，则不再执行方法体，而从缓存中直接返回。当方法返回null时，将不进行缓存操作。
    // @CachePut ：表示执行该方法后，其值将作为最新结果更新到缓存中，每次都会执行该方法。
    // @CacheEvict ：表示执行该方法后，将触发缓存清除操作。
    // @Caching ：用于组合前三个注解，例如：
    // cacheNames/value ：缓存组件的名字，即cacheManager中缓存的名称。 SpringCache
    // key ：缓存数据时使用的key。默认使用方法参数值，也可以使用SpEL表达式进行编写。
    // keyGenerator ：和key二选一使用。
    // cacheManager ：指定使用的缓存管理器。
    // condition ：在方法执行开始前检查，在符合condition的情况下，进行缓存
    // unless ：在方法执行完成后检查，在符合unless的情况下，不进行缓存
    // sync ：是否使用同步模式。若使用同步模式，在多个线程同时对一个key进行load时，其他线程将被阻塞。
    @Caching(cacheable = @Cacheable("CacheConstants.GET_USER"),
            evict = {@CacheEvict(value = "CacheConstants.GET_DYNAMIC", allEntries = true)})
    public User find(Integer id) {
        return null;
    }

}
