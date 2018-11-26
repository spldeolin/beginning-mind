package com.spldeolin.beginningmind.core.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import com.spldeolin.beginningmind.core.CoreProperties;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/12
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
    private RabbitProperties rabbitProperties;

    @Autowired
    private ManagementServerProperties managementServerProperties;

    @Autowired
    private MybatisPlusProperties mybatisPlusProperties;

    @Autowired
    private PageHelperProperties pageHelperProperties;

    @Autowired
    private CoreProperties coreProperties;

    @Test
    @SneakyThrows
    public void t() {
        // spring.datasource
        log.info(dataSourceProperties);

        // spring.redis
        log.info(redisProperties);

        // spring.rabbitmq
        log.info(rabbitProperties);

        // logging
        // 找不到ConfigurationProperties的类

        // management
        log.info(managementServerProperties);

        // mybatisplus
        log.info(mybatisPlusProperties);

        // pagehelper
        log.info(pageHelperProperties);

        // core
        log.info(coreProperties);

        log.info("结束");
    }

}