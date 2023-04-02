package com.spldeolin.beginningmind.config;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spldeolin.beginningmind.util.JsonUtils;

/**
 * 配置用于转化@RequestBody和@ResponseBody对象ObjectMapper
 *
 * @author Deolin
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();

        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.setTimeZone(TimeZone.getDefault());

        // Java8 LocalDateTime, LocalDate, LocalTime default format
        om.registerModule(JsonUtils.java8timeFormatModule());

        // Java1 Date default format
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return om;
    }

}
