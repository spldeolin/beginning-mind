package com.spldeolin.beginningmind.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis包扫描配置
 *
 * @author Deolin 2018/11/10
 */
@MapperScan(basePackages = "com.spldeolin.beginningmind.mapper")
@Configuration
public class MybatisConfig {

}
