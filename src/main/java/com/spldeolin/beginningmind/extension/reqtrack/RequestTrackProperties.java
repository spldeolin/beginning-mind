package com.spldeolin.beginningmind.extension.reqtrack;

import java.util.Collection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import lombok.Data;

/**
 * 配置一览
 *
 * @author Deolin
 */
@Component
@ConfigurationProperties(value = "request-track")
@Data
public class RequestTrackProperties {

    /**
     * curl长度限制
     */
    private Integer curlMaxLength = 2000;

    /**
     * responseBody的长度限制
     */
    private Integer responseBodyMaxLength = 100000;

    /**
     * 需要隐藏的request header names
     */
    private Collection<String> hiddenRequestHeaderNames = Lists.newArrayList();

    /**
     * 需要隐藏的response header names
     */
    private Collection<String> hiddenResponseHeaderNames = Lists.newArrayList();

}