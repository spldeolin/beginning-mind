package com.spldeolin.beginningmind.core.config;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.CoreProperties;

/**
 * 配置用于转化@RequestBody和@ResponseBody对象ObjectMapper
 *
 * @author Deolin
 */
@Configuration
public class JacksonConfig {

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();

        // 使ObjectMapper支持Guava Collection、java.time包
        // 使Long类型作为String类型转化为JSON
        builder.modules(Lists.newArrayList(guavaCollectionModule(), javaTimeModule(), longToStringModulel()));

        // 是否将java.util.Date对象转化为时间戳
        if (!Optional.ofNullable(coreProperties.getTime().getSerializeJavaUtilDateToTimestamp()).orElse(true)) {
            builder.simpleDateFormat(coreProperties.getTime().getDefaultDatetimePattern());
        }

        // 忽略不认识的属性名
        builder.failOnUnknownProperties(false);

        // 时区
        builder.timeZone(TimeZone.getDefault());

        return builder;
    }

    private GuavaModule guavaCollectionModule() {
        return new GuavaModule();
    }

    private SimpleModule javaTimeModule() {
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

        return javaTimeModule;
    }

    private SimpleModule longToStringModulel() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return simpleModule;
    }

}
