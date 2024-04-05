package com.spldeolin.beginningmind.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Deolin 2019-01-22
 */
@ComponentScan({"com.spldeolin.beginningmind.app", "com.spldeolin.satisficing.framework.app"})
@MapperScan(basePackages = "com.spldeolin.beginningmind.app.mapper")
@EnableFeignClients(basePackages = "com.spldeolin.beginningmind.api.**")
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
