package com.spldeolin.beginningmind.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 配置一览
 *
 * @author Deolin
 */
@Component
@ConfigurationProperties(value = "core")
@Data
public class CoreProperties {

    /**
     * SpringBoot内部的@Async线程池规格
     */
    private TaskExecutorProp taskExecutor;

    /**
     * 雪花算法的机器区分ID和数据库区分ID
     */
    private SnowFlakeProp snowFlake;

    /**
     * E-Mail
     */
    private EmailProp email;

    /**
     * OSS
     */
    private OssProp oss;

    /**
     * 是否启用安全模块
     */
    private Boolean enableSecurity;

    @Data
    public static class TaskExecutorProp {

        private Integer coreSize;

        private Integer maximumSize;

        private Integer queueCapacity;

        private Integer keepAliveSeconds;

    }

    @Data
    public static class SnowFlakeProp {

        private Long datacenterId;

        private Long machineId;

    }

    @Data
    public static class EmailProp {

        private String serverHost;

        private Integer serverPort;

        private String addresserName;

        private String addresserEmail;

        private String addresserAuthCode;

    }

    @Data
    public static class OssProp {

        private String buckeName;

        private String endPoint;

        private String accessKeyId;

        private String accessKeySecret;

        private String fileHost;

    }

}