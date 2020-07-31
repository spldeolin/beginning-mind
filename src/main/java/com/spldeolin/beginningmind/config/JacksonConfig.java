package com.spldeolin.beginningmind.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spldeolin.beginningmind.support.DeserializeIgnoreNullElementModule;
import com.spldeolin.beginningmind.util.JsonUtils;
import com.spldeolin.beginningmind.support.StringTrimModule;

/**
 * 配置用于转化@RequestBody和@ResponseBody对象ObjectMapper
 *
 * @author Deolin
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = JsonUtils.initObjectMapper(new ObjectMapper());

        // 反序列化时，忽略Collection中为null的元素
        om.registerModule(new DeserializeIgnoreNullElementModule());

        // 反序列化时，对每个String进行trim
        om.registerModule(new StringTrimModule());

        return om;
    }

}
