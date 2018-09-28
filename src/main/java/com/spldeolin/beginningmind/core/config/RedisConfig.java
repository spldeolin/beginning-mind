package com.spldeolin.beginningmind.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.spldeolin.beginningmind.core.redis.ProtostuffSerializer;
import lombok.extern.log4j.Log4j2;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public JedisPool jedisPool(
            @Autowired JedisPoolConfig config,
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.timeout}") int timeout,
            @Value("${spring.redis.password}") String password) {
        return new JedisPool(config, host, port, timeout, password);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(
            @Value("${spring.redis.pool.max-active}") int maxTotal,
            @Value("${spring.redis.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.pool.max-wait}") int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

}
