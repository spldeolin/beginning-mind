package com.spldeolin.beginningmind.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Deolin 2019-01-22
 */
@ComponentScan(basePackageClasses = Application.class)
@MapperScan("com.spldeolin.beginningmind.app.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
