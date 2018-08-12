package com.spldeolin.beginningmind.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * 配置一览
 */
@Component
@ConfigurationProperties(value = "core")
@Data
@Log4j2
public class CoreProperties {

    /**
     * 是否处于debug场合
     */
    @Value("${debug}")
    private boolean debug;

    /**
     * 本项目的访问地址（IP:端口号，e.g.: http:localhost:2333）
     */
    private String address;

    /**
     * Redis缓存的key命名空间
     */
    private String redisNamespace;

    /**
     * “时间”格式
     */
    private Time time;

    @Data
    public static class Time {

        private String defaultDatePattern;

        private String defaultTimePattern;

        private String defaultDatetimePattern;

        private Boolean serializeJavaUtilDateToTimestamp;

    }

    /**
     * 文件支持
     */
    private File file;

    @Data
    public static class File {

        private String mapping;

        private String location;

    }

    /**
     * E-Mail
     */
    private Email email;

    @Data
    public static class Email {

        private String serverHost;

        private Integer serverPort;

        private String addresserName;

        private String addresserEmail;

        private String addresserAuthCode;

    }

}