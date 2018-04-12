package com.spldeolin.beginningmind.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(Properties.PREFIX + ".alidayu")
@Data
public class AlidayuProperties {

    private String appid;

    private String appsecret;

    private String signName;

    private String templateCode;

}
