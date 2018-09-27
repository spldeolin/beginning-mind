package com.spldeolin.beginningmind.core.redis;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * 基于Redis的分布式锁
 *
 * @author Deolin 2018/09/27
 */
@Component
public class DistributedLock {

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    @Autowired
    private Jedis jedis;

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey 锁的唯一Key
     * @param requestId 用于区分锁是被哪个请求获取的，可以使用UUID
     * @param expireTime 锁的超时时间（锁如果长时间未被释放，则自动释放）
     * @return 是否成功获取锁
     */
    public boolean get(String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey 锁的唯一Key
     * @param requestId 传入获取锁时使用的requestId
     * @return 是否成功释放了锁
     */
    public boolean release(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }

}
