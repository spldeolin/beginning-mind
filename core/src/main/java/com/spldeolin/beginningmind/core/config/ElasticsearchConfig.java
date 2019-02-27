package com.spldeolin.beginningmind.core.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author Deolin 2019-02-27
 */
@Configuration
public class ElasticsearchConfig {

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

}
