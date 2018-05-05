package com.spldeolin.beginningmind.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 配置一览
 */
@Component
@ConfigurationProperties(value = "beginning-mind")
@Data
public class BeginningMindProperties {

    /**
     * “一个曲奇”
     */
    private String oneCookie;

    /**
     * 本项目的访问地址（IP:端口号，e.g.: http:localhot:2333）
     */
    private String address;

    /**
     * HTTP请求 页码的URL参数名
     */
    private String pageNoParamName;

    /**
     * HTTP请求 分页尺寸的URL参数名
     */
    private String pageSizeParamName;

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

}