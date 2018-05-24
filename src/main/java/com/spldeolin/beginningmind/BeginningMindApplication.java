package com.spldeolin.beginningmind;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeginningMindApplication {

    public static void main(String[] args) {
        if (!StringUtils.equalsAny(System.getProperty("profile"), "dev", "prod")) {
            System.out.println("没有正确指定-Dprofile，启动被阻止。");
            return;
        }
        SpringApplication.run(BeginningMindApplication.class, args);
    }

}