package com.spldeolin.beginningmind.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "beginning-mind")
@Data
public class GlobalProperties {

    private String oneCookie;

}
