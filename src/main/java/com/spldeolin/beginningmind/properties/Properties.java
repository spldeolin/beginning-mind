package com.spldeolin.beginningmind.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 配置一览
 */
@Component
@ConfigurationProperties(Properties.PREFIX)
@Data
public class Properties {

    public static final String PREFIX = "beginning-mind";

    /**
     * “一个曲奇”
     */
    private String oneCookie;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 使用一分钱支付
     */
    private Boolean useOneCentWhenPay;

    /**
     * 时间格式配置
     */
    @Autowired
    private TimeProperties timeProperties;

    /**
     * 中文简体文案配置
     */
    @Autowired
    private TextZhHansProperties textZhHansProperties;

    /**
     * 日语文案配置
     */
    @Autowired
    private TextJaJpProperties textJaJpProperties;

    /**
     * 微信配置（第三方）
     */
    @Autowired
    private WechatProperties wechatProperties;

    /**
     * 阿里大鱼配置（第三方）
     */
    @Autowired
    private AlidayuProperties alidayuProperties;

}