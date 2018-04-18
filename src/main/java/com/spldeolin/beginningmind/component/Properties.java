package com.spldeolin.beginningmind.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

/**
 * 配置一览
 */
@Component
@ConfigurationProperties(Properties.PREFIX)
public class Properties {

    public static final String PREFIX = "beginning-mind";

    /**
     * “一个曲奇”
     */
    @Getter
    @Setter
    public static String OneCookie;

    /**
     * IP地址
     */
    @Getter
    @Setter
    private static String ip;

    /**
     * 版本号
     */
    @Getter
    @Setter
    private static Integer version;

    /**
     * 使用一分钱支付
     */
    @Getter
    @Setter
    private static Boolean useOneCentWhenPay;

    /**
     * 时间格式配置
     */
    @Component
    @ConfigurationProperties(Properties.PREFIX + ".time")
    public static class TimeProperties {

        @Getter
        @Setter
        private static String defaultDatePattern;

        @Getter
        @Setter
        private static String defaultTimePattern;

        @Getter
        @Setter
        private static String defaultDatetimePattern;

        @Getter
        @Setter
        private static Boolean serializeJavaUtilDateToTimestamp;

    }

    /**
     * 中文简体文案配置
     */
    @Component
    @ConfigurationProperties(Properties.PREFIX + ".text-zh-hans")
    public static class TextZhHansProperties {

        @Getter
        @Setter
        private static String hello;

        @Getter
        @Setter
        private static String excellent;

    }

    /**
     * 日语文案配置
     */
    @Component
    @ConfigurationProperties(Properties.PREFIX + ".text-ja-jp")
    public static class TextJaJpProperties {

        @Getter
        @Setter
        private static String hello;

        @Getter
        @Setter
        private static String excellent;

    }

    /**
     * 微信配置（第三方）
     */
    @Component
    @ConfigurationProperties(Properties.PREFIX + ".wechat")
    public static class WechatProperties {

        @Getter
        @Setter
        private static String appid;

        @Getter
        @Setter
        private static String appsecret;

    }

    /**
     * 阿里大鱼配置（第三方）
     */
    @Component
    @ConfigurationProperties(Properties.PREFIX + ".alidayu")
    public static class AlidayuProperties {

        @Getter
        @Setter
        private static String appid;

        @Getter
        @Setter
        private static String appsecret;

        @Getter
        @Setter
        private static String signName;

        @Getter
        @Setter
        private static String templateCode;

    }

}