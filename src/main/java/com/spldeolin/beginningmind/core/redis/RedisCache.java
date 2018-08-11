package com.spldeolin.beginningmind.core.redis;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.Times;
import lombok.extern.log4j.Log4j2;

/**
 * “缓存不存在”、“缓存失效”、“缓存被删除”三者等价
 *
 * @author Deolin 2018/08/10
 */
@Component
@Log4j2
public class RedisCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 删除一个缓存
     *
     * 不存在则什么都不会发生
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除一批Redis缓存
     *
     * 忽略不存在的缓存
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 判断Redis缓存是否存在
     */
    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 更新缓存的过期时间
     *
     * key不存在则返回false
     */
    public Boolean updateExpire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 更新缓存的过期时间
     *
     * key不存在则返回false
     */
    public Boolean updateExpire(String key, LocalDateTime localDateTime) {
        return redisTemplate.expireAt(key, Times.toDate(localDateTime));
    }

    /**
     * 查找匹配的key
     */
    public Set<String> searchKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除缓存的失效时间，使缓存永远有效
     *
     * 缓存不存在返回false
     */
    public Boolean deleteExpire(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 获取缓存的剩余失效时间（TODO key不存在？）
     *
     * 缓存不存在则返回-2
     * 缓存永久有效则返回-1
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 获取指定缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 创建一个永不失效的缓存
     *
     * 如果key对应的缓存存在，则覆盖原缓存
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)（TODO key不存在？）（TODO 过期时间？）
     */
    @SuppressWarnings("unchecked")
    public <T> T getAndSet(String key, String value) {
        return (T) redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 批量获取
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> multiGet(Collection<String> keys) {
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        List<T> result = Lists.newArrayList();
        objects.forEach(obj -> result.add((T) obj));
        return result;
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout（TODO key存在，覆盖原value吗？）
     *
     * @param timeout 过期时间
     * @param unit 时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES 秒:TimeUnit.SECONDS
     * 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @return 之前已经存在返回false, 不存在返回true
     */
    public boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量添加
     */
    public void multiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @return 之前已经存在返回false, 不存在返回true
     */
    public boolean multiSetIfAbsent(Map<String, String> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

}
