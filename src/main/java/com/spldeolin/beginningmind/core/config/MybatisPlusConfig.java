package com.spldeolin.beginningmind.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;

/**
 * @author Deolin 2018/11/10
 */
@MapperScan("com.spldeolin.beginningmind.core.dao")
@Configuration
public class MybatisPlusConfig {

    /**
     * 启用乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
