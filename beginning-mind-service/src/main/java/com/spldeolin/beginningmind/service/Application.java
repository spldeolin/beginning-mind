package com.spldeolin.beginningmind.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Deolin 2019-01-22
 */
@ComponentScan("com.spldeolin.beginningmind.service")
@MapperScan("com.spldeolin.beginningmind.service.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
