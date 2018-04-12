package com.spldeolin.beginningmind.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(Properties.PREFIX + ".wechat")
@Data
public class WechatProperties {

    private String appid;

    private String appsecret;

}
