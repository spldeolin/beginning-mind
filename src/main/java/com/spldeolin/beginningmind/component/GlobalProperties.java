package com.spldeolin.beginningmind.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "beginning-mind")
@Data
public class GlobalProperties {

    private String defaultDatePattern;

    private String defaultTimePattern;

    private String defaultDatetimePattern;

    private Boolean serializeJavaUtilDateToTimestamp;

    private String oneCookie;

}
