package cn.xqhuang.dps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class RedisLockConfiguration {
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        //第一个参数redisConnectionFactory
        //第二个参数registryKey，分布式锁前缀，设置为项目名称会好些
        //该构造方法对应的分布式锁，默认有效期是60秒.可以自定义
        return new RedisLockRegistry(redisConnectionFactory, "boot-launch");
        //return new RedisLockRegistry(redisConnectionFactory, "boot-launch",60);
    }
}
