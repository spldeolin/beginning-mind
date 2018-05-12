package com.spldeolin.beginningmind.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private BeginningMindProperties properties;

    /**
     * 额外监听路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String mapping = properties.getFile().getMapping();
        String location = properties.getFile().getLocation();
        //  addResourceLocations参数必须以/结果，否则识别不到
        if (!StringUtils.endsWith(location, "/")) {
            location += "/";
        }
        registry.addResourceHandler(mapping + "/**").addResourceLocations("file:" + location);
    }

    /**
     * 跨域
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

}
