package com.spldeolin.beginningmind.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.spldeolin.beginningmind.core.redis.ProtostuffSerializer;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/07/16
 */
@Configuration
@Log4j2
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> overrideSerializer(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setEnableTransactionSupport(true);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);

        ProtostuffSerializer valueSerializer = new ProtostuffSerializer();
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(valueSerializer);
        return redisTemplate;
    }

}
