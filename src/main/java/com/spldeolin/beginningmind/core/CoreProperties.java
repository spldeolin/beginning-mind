package com.spldeolin.beginningmind.core;

import java.net.InetAddress;
import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * 配置一览
 *
 * @author Deolin
 */
@ConfigurationProperties(value = "core")
@Component
@Data
public class CoreProperties {

    /**
     * 本项目的访问地址（http://localhost:2333）
     */
    private String address;

    /**
     * Redis缓存的key命名空间
     */
    private String redisNamespace;

    /**
     * “时间”格式
     */
    private TimeProp time;

    @Data
    public static class TimeProp {

        private String defaultDatePattern;

        private String defaultTimePattern;

        private String defaultDatetimePattern;

        private Boolean serializeJavaUtilDateToTimestamp;

    }

    /**
     * SpringBoot内部的@Async线程池规格
     */
    private TaskExecutorProp taskExecutor;

    @Data
    public static class TaskExecutorProp {

        private Integer coreSize;

        private Integer maximumSize;

        private Integer queueCapacity;

        private Integer keepAliveSeconds;

    }

    /**
     * 雪花算法的机器区分ID和数据库区分ID
     */
    private SnowFlakeProp snowFlake;

    @Data
    public static class SnowFlakeProp {

        private Long datacenterId;

        private Long machineId;

    }

    /**
     * E-Mail
     */
    private EmailProp email;

    @Data
    public static class EmailProp {

        private String serverHost;

        private Integer serverPort;

        private String addresserName;

        private String addresserEmail;

        private String addresserAuthCode;

    }

    /**
     * OSS
     */
    private OssProp oss;

    @Data
    public static class OssProp {

        private String buckeName;

        private String endPoint;

        private String accessKeyId;

        private String accessKeySecret;

        private String fileHost;

    }

    /**
     * 是否启用安全模块
     */
    private Boolean enableSecurity;

    /**
     * 是否启用Swagger
     */
    private Boolean enableSwagger;

    /**
     * 将配置中出现的localhost替换为本地IP
     */
    @PostConstruct
    @SneakyThrows
    public void localhostToLocalIp() {
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
    }

}