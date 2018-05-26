package com.spldeolin.beginningmind.config;

import java.net.InetAddress;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 配置一览
 */
@Component
@ConfigurationProperties(value = "beginning-mind")
@Data
@Log4j2
public class BeginningMindProperties {

    /**
     * 是否处于debug场合
     */
    @Value("${debug}")
    private boolean debug;

    /**
     * “一个曲奇”
     */
    private String oneCookie;

    /**
     * 本项目的访问地址（IP:端口号，e.g.: http:localhost:2333）
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

    @Value("${server.context-path}")
    private String serverContextPath;

    /**
     * 将配置中出现的所有localhost替换为本地IP
     */
    @SneakyThrows
    public void localhostToLocalIp() {
        address = address.replace("localhost", InetAddress.getLocalHost().getHostAddress());
    }

    /**
     * 在file.mapping前追加content-path
     */
    @PostConstruct
    public void mappingAppendContextPath() {
        if (StringUtils.isNotBlank(serverContextPath) && !serverContextPath.equals("/")) {
            String mapping = serverContextPath + file.getMapping();
            log.info("由于指定了context-path为" + serverContextPath + "，故file.mapping变更为" + mapping);
            file.setMapping(mapping);
        }
    }

    /**
     * 确保file.mapping以 / 开头和结尾，确保file.location以 / 结尾
     */
    @PostConstruct
    public void validateProperties() {
        if (!file.getMapping().endsWith("/")) {
            throw new IllegalArgumentException("beginning-mind.file.mapping必须以 / 结尾");
        }
        if (!file.getLocation().endsWith("/")) {
            throw new IllegalArgumentException("beginning-mind.file.location必须以 / 结尾");
        }
        if (!file.getMapping().endsWith("/")) {
            throw new IllegalArgumentException("beginning-mind.file.mapping必须以 / 开头");
        }
    }

}