package com.spldeolin.beginningmind.cache;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.util.SerializationUtil;

@Component
public class RedisCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> void setCache(String key, T obj) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializationUtil.serialize(obj);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(bkey, bvalue);
                return null;
            }
        });
    }

    /**
     * 如果该key不存在，则加入缓存，注意，如果已经设置了这个Key的缓存，那么即使值更新了也不会刷新缓存（Set If Not eXists）
     */
    public <T> boolean setNXCache(String key, T obj) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializationUtil.serialize(obj);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(bkey, bvalue);
            }
        });
        return result;
    }

    /**
     * 单位：秒
     */
    public <T> void setCacheWithExpireTime(String key, T obj, final long expireTime) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = SerializationUtil.serialize(obj);
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
    }

    public <T> T getCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return SerializationUtil.deserialize(result, targetClass);
    }

    /**
     * 精确删除key
     */
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 模糊删除key
     */
    public void deleteCacheWithPattern(String pattern) {
        Set<String> keys = redisTemplate.keys("*" + pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 清空所有缓存
     */
    public void clearCache() {
        deleteCacheWithPattern("*");
    }

}
