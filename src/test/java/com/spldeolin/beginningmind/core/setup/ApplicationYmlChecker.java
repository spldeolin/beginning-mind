package com.spldeolin.beginningmind.core.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.spldeolin.beginningmind.core.CoreProperties;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

//import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author Deolin 2018/08/01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@Log4j2
public class ApplicationYmlChecker {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private MongoProperties mongoProperties;

    @Autowired
    private RabbitProperties rabbitProperties;

    @Autowired
    private ManagementServerProperties managementServerProperties;

    @Autowired
    private MybatisProperties mybatisProperties;

    @Autowired
    private tk.mybatis.mapper.autoconfigure.MybatisProperties tkMybatisProperties;

    @Autowired
    private CoreProperties coreProperties;

    @Test
    @SneakyThrows
    public void t() {
        // spring.datasource.druid
        log.info(dataSourceProperties);

        // spring.redis
        log.info(redisProperties);

        // spring.data.mongodb
        log.info(mongoProperties);

        // spring.rabbitmq
        log.info(rabbitProperties);

        // spring.management
        log.info(managementServerProperties);

        // spring.mybatis
        log.info(mybatisProperties);
        log.info(tkMybatisProperties);

        // mapper
        // pagehelper
        // 找不到可以autowired的bean

        // logging
        // 找不到ConfigurationProperties的类

        // core
        log.info(coreProperties);

        log.info("结束");
    }

}