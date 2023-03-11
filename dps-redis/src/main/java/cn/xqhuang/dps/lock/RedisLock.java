package cn.xqhuang.dps.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huangxq
 * @description: TODO
 * @date 2022/11/817:03
 */
public class RedisLock {

    @Resource
    private RedissonClient redissonClient;


    /**
     *   ----------------重设过期时间 ---------------------
     *   "if (redis.call('exists', KEYS[1]) == 0) then " +
     *             "redis.call('incrby', KEYS[1], ARGV[2], 1); " +
     *             "redis.call('expire', KEYS[1], ARGV[1]); " +
     *             "return nil; " +
     *             "end; " +
     *             "if (redis.call('exists', KEYS[1], ARGV[2]) == 1) then " +
     *             "redis.call('incrby', KEYS[1], ARGV[2], 1); " +
     *             "redis.call('expire', KEYS[1], ARGV[1]); " +
     *             "return nil; " +
     *             "end; " +
     *             "return redis.call('pttl', KEYS[1]);"
     *
     *   ----------------重设过期时间 ---------------------
     *  "if (redis.call('exists', KEYS[1], ARGV[2]) == 1) then " +
     *                     "redis.call('expire', KEYS[1], ARGV[1]); " +
     *                     "return 1; " +
     *                     "end; " +
     *                     "return 0;"
     *
     *    ----------------- 释放锁 ---------------------
     *   "if (redis.call('exists', KEYS[1], ARGV[3]) == 0) then " +
     *                     "return nil;" +
     *                     "end; " +
     *                     "local counter = redis.call('incrby', KEYS[1], ARGV[3], -1); " +
     *                     "if (counter > 0) then " +
     *                     "redis.call('expire', KEYS[1], ARGV[2]); " +
     *                     "return 0; " +
     *                     "else " +
     *                     "redis.call('del', KEYS[1]); " +
     *                     "redis.call('publish', KEYS[2], ARGV[1]); " +
     *                     "return 1; " +
     *                     "end; " +
     *                     "return nil;"
     * @param lockName
     */
    public void reentrantLock(String lockName) {
        RLock reentrantLock = redissonClient.getLock(lockName);

        reentrantLock.lock();
        try {
            // do something
        } finally {
            reentrantLock.unlock();
        }
    }

    public void release() {
        this.redissonClient.shutdown();
    }

}
