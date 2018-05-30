package com.spldeolin.beginningmind.core.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.spldeolin.beginningmind.core.CoreProperties;

@Configuration
public class JacksonConfig {

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern(coreProperties.getTime().getDefaultDatePattern());
        DateTimeFormatter time = DateTimeFormatter.ofPattern(coreProperties.getTime().getDefaultTimePattern());
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern(
                coreProperties.getTime().getDefaultDatetimePattern());
        SimpleModule javaTimeModule = new JavaTimeModule();

        // 三大时间对象 序列器
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(date))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(time))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTime));
        // 三大时间对象 反序列器
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(date))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(time))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTime));

        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule);
        // 是否将java.util.Date对象转化为时间戳
        if (!Optional.ofNullable(coreProperties.getTime().getSerializeJavaUtilDateToTimestamp()).orElse(
                true)) {
            builder.simpleDateFormat(coreProperties.getTime().getDefaultDatetimePattern());
        }
        // 忽略不认识的属性名
        builder.failOnUnknownProperties(true);
        // 时区
        builder.timeZone("GMT+8");
        return builder;
    }

}
