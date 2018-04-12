package com.spldeolin.beginningmind.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(Properties.PREFIX + ".time")
@Data
public class TimeProperties {

    private String defaultDatePattern;

    private String defaultTimePattern;

    private String defaultDatetimePattern;

    private Boolean serializeJavaUtilDateToTimestamp;

}
