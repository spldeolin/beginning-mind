package com.spldeolin.beginningmind.api;

import javax.annotation.PostConstruct;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2025-02-09
 */
@Configuration("beginning-mind-apiAutoConfiguration")
@ComponentScan("com.spldeolin.beginningmind.api")
@EnableFeignClients("com.spldeolin.beginningmind.api")
@Slf4j
public class ApiAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("beginning-mind-api is auto configured");
    }

}
