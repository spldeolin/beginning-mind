package com.spldeolin.beginningmind.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Deolin
 */
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        if (StringUtils.isBlank(System.getProperty("server"))) {
            System.out.println("没有指定-Dserver，启动被阻止。");
            return;
        }
        if (!StringUtils.equalsAny(System.getProperty("profile"), "dev", "test", "prod")) {
            System.out.println("没有正确地指定-Dprofile，启动被阻止。");
            return;
        }

        SpringApplication.run(CoreApplication.class, args);
    }

}