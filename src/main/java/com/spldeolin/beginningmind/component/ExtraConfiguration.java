package com.spldeolin.beginningmind.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.spring4all.swagger.EnableSwagger2Doc;

@Component
@Configuration
@EnableSwagger2Doc
@EnableRedisHttpSession
public class ExtraConfiguration {

    @Autowired
    private GlobalProperties globalProperties;

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern(globalProperties.getDefaultDatePattern());
        DateTimeFormatter time = DateTimeFormatter.ofPattern(globalProperties.getDefaultTimePattern());
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern(globalProperties.getDefaultDatetimePattern());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // date Json化和反Json化
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(date));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(date));
        // time  Json化和反Json化
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(time));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(time));
        // datetime Json化和反Json化
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTime));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTime));
        return new ObjectMapper().registerModule(javaTimeModule);
    }


}