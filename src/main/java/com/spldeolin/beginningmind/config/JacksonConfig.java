package com.spldeolin.beginningmind.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spldeolin.beginningmind.util.ObjectMapperUtils;

/**
 * 配置用于转化@RequestBody和@ResponseBody对象ObjectMapper
 *
 * @author Deolin
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperUtils.initDefault(new ObjectMapper());
    }

}
