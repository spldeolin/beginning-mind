package com.spldeolin.beginningmind.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.spring4all.swagger.EnableSwagger2Doc;

@Configuration
@EnableSwagger2Doc
@EnableRedisHttpSession
public class ExtraConfiguration {

    @Autowired
    private Properties properties;

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern(properties.getTime().getDefaultDatePattern());
        DateTimeFormatter time = DateTimeFormatter.ofPattern(properties.getTime().getDefaultTimePattern());
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern(
                properties.getTime().getDefaultDatetimePattern());
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
        if (!Optional.ofNullable(properties.getTime().getSerializeJavaUtilDateToTimestamp()).orElse(true)) {
            builder.simpleDateFormat(properties.getTime().getDefaultDatetimePattern());
        }
        // 蛇形分割的属性名
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // 忽略不认识的属性名
        builder.failOnUnknownProperties(true);
        // 时区
        builder.timeZone("GMT+8");
        return builder;
    }

}