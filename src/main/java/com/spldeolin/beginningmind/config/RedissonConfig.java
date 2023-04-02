package com.spldeolin.beginningmind.config;

import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * Redisson配置
 *
 * @author Deolin 2018/07/16
 */
@Configuration
@Slf4j
public class RedissonConfig {

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public void redissonClient() {
        redissonClient.getConfig().setCodec(new FstCodec());
    }

}
