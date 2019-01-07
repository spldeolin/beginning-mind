package com.spldeolin.beginningmind.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.log4j.Log4j2;

/**
 * 基于Redis的分布式锁（不可重入）
 *
 * @author Deolin 2018/09/27
 */
@Component
@Log4j2
public class RedisLock {

    @Autowired
    private RedisCommands<String, String> redisCommands;

    /**
     * 上分布式锁
     *
     * @param lockKey key
     * @param threadValue 代表线程的唯一值
     * @param expireMilli 锁的超时时间（毫秒）（锁如果长时间未被释放，则自动释放）
     * @return 是否成功获取锁
     */
    public boolean lock(String lockKey, String threadValue, int expireMilli) {
        return "OK".equals(redisCommands.set(lockKey, threadValue, SetArgs.Builder.nx().px(expireMilli)));
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey key
     * @param threadValue 代表线程的唯一值
     * @return 是否成功释放了锁
     */
    public boolean release(String lockKey, String threadValue) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1])"
                + " else return 0 end";
        return redisCommands.eval(script, ScriptOutputType.BOOLEAN, new String[]{lockKey}, threadValue);
    }

}
