package com.spldeolin.beginningmind.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(Properties.PREFIX + ".text-ja-jp")
@Data
public class TextJaJpProperties {

    private String hello;

    private String excellent;

}
