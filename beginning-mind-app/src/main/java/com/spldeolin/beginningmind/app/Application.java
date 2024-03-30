package com.spldeolin.beginningmind.app;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spldeolin.satisficing.framework.app.util.JsonUtils;

/**
 * @author Deolin 2019-01-22
 */
@ComponentScan({"com.spldeolin.beginningmind.app", "com.spldeolin.satisficing.app"})
@MapperScan(basePackages = "com.spldeolin.beginningmind.app.mapper")
@EnableFeignClients(basePackages = "com.spldeolin.**") // TODO precision
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

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

    @Value("${thread-pool.core-size}")
    private Integer coreSize;

    @Value("${thread-pool.maximum-size}")
    private Integer maximumSize;

    @Value("${thread-pool.queue-capacity}")
    private Integer queueCapacity;

    @Value("${thread-pool.keep-alive-seconds}")
    private Integer keepAliveSeconds;

    @Bean("globalThreadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, maximumSize, keepAliveSeconds, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity), new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }

}
