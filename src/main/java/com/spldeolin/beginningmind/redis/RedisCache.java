package com.spldeolin.beginningmind.redis;

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
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.util.Times;

/**
 * Redis缓存 管理
 *
 * 注释中的“缓存不存在”、“缓存失效”、“缓存被删除”三者等价
 *
 * @author Deolin 2018/08/10
 */
@Component
public class RedisCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建一个有失效时间的缓存
     *
     * 如果key对应的缓存存在，则覆盖value和失效时间
     */
    public <T> void set(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 创建一个缓存
     *
     * 缓存永不失效
     *
     * 如果key对应的缓存存在，则覆盖原缓存
     */
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 创建一个缓存
     *
     * 缓存永不失效
     *
     * 如果key对应的缓存存在，则什么都不发生，并返回false
     */
    public <T> boolean setIfAbsent(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量创建缓存
     *
     * 缓存永不失效
     */
    public <T> void multiSet(Map<String, T> map) {
        Map<String, T> newMap = Maps.newHashMap();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
        }
        redisTemplate.opsForValue().multiSet(newMap);
    }

    /**
     * 批量创建缓存
     *
     * 缓存永不失效
     *
     * 如果任意一个key对应的缓存存在，则什么都不发生，并返回false
     */
    public <T> boolean multiSetIfAllAbsent(Map<String, T> maps) {
        Map<String, T> newMap = Maps.newHashMap();
        for (Map.Entry<String, T> entry : maps.entrySet()) {
            newMap.put(entry.getKey(), entry.getValue());
        }
        return redisTemplate.opsForValue().multiSetIfAbsent(newMap);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * 如果原缓存不存在，则返回null，并创建一个新的缓存
     *
     * 无论是覆盖原缓存还是创建新缓存，最终key对应的缓存是永不失效的
     */
    @SuppressWarnings("unchecked")
    public <T> T getAndSet(String key, T value) {
        return (T) redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 获取缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 查找匹配的key
     */
    public Set<String> searchKeys(String pattern) {
        return redisTemplate.keys(pattern);
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
     * 获取缓存的剩余失效时间
     *
     * 缓存不存在则返回-2 缓存永久有效则返回-1
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
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
     * 删除缓存的失效时间，使缓存永远有效
     *
     * 缓存不存在返回false
     */
    public Boolean deleteExpire(String key) {
        return redisTemplate.persist(key);
    }

}