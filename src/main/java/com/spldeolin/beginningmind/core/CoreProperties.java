package com.spldeolin.beginningmind.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * 配置一览
 *
 * @author Deolin
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
     * api.mapper.provider包下用的审计字段与删除标记配置
     */
    private MapperProvider mapperProvider;

    @Data
    public static class MapperProvider {

        private List<String> auditingFieldNames;

        private List<String> auditingPropertyNames;

        private boolean isExistDeletionFlag;

        private String deletionFlagFieldName;

        private String deletionFlagPropertyName;

        private String isNotDeletedSql;

    }

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

    /**
     * 雪花算法的机器区分ID和数据库区分ID
     */
    private SnowFlake snowFlake;

    @Data
    public static class SnowFlake {

        private Long datacenterId;

        private Long machineId;

    }

    /**
     * CommonServiceImpl中每个LoadingCache对象的最大缓存条数
     */
    private Integer maxSizePerLocalCache;

}