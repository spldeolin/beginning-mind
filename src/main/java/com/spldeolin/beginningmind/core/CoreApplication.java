package com.spldeolin.beginningmind.core;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class CoreApplication {

    public static void main(String[] args) {
        if (!StringUtils.equalsAny(System.getProperty("profile"), "dev", "test", "prod")) {
            log.fatal("没有正确指定-Dprofile，启动被阻止。");
            System.exit(0);
        }

        SpringApplication.run(CoreApplication.class, args);
    }

}