package com.spldeolin.beginningmind.launch;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Deolin 2019-01-22
 */
@ComponentScan("com.spldeolin.beginningmind")
@SpringBootApplication
public class Startup {

    public static void main(String[] args) {
        if (StringUtils.isBlank(System.getProperty("server"))) {
            System.out.println("没有指定-Dserver，启动被阻止。");
            return;
        }

        if (!StringUtils.equalsAny(System.getProperty("profile"), "dev", "test", "prod")) {
            System.out.println("没有正确地指定-Dprofile，启动被阻止。");
            return;
        }

        SpringApplication.run(Startup.class, args);
    }

}
