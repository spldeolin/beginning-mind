package com.spldeolin.beginningmind.component;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.spldeolin.beginningmind.component.shiro.CredentialsMatcher;
import com.spldeolin.beginningmind.component.shiro.Realm;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableSwagger2Doc
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1801) // Session保存时间、登录超时时间均在这里设置（单位：秒）
public class ConfigurationBean {

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

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 不再重定向到登录页面，而是返回前端code401
        shiroFilterFactoryBean.setLoginUrl("/unauthc");
        // 不再重定向到未授权页面，而是返回前端code403
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/shiro/need_sign_in", "authc");
        filterChainDefinitionMap.put("/shiro/sign_out", "authc");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager(AuthorizingRealm realm, CacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean
    public AuthorizingRealm realm(SimpleCredentialsMatcher simpleCredentialsMatcher, CacheManager cacheManager) {
        AuthorizingRealm realm = new Realm();
        realm.setCredentialsMatcher(simpleCredentialsMatcher);
        realm.setCacheManager(cacheManager);
        return realm;
    }

    @Bean
    public SimpleCredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public RedisCacheManager cacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    @Bean
    public RedisManager redisManager(
            @Value("${spring.redis.host}") String host,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.timeout}") int timeout,
            @Value("${spring.redis.password}") String password) {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

}