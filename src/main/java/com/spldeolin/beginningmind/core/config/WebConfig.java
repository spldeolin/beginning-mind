package com.spldeolin.beginningmind.core.config;

import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.spldeolin.beginningmind.core.CoreProperties;

/**
 * Web配置
 *
 * @author Deolin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CoreProperties coreProperties;

    /**
     * 额外监听路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(coreProperties.getFile().getMapping() + "/**")
                .addResourceLocations("file:" + coreProperties.getFile().getLocation());
    }

    /**
     * 跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    /**
     * 文件上传
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单文件限制
        factory.setMaxFileSize(DataSize.ofMegabytes(5));
        // 总文件限制
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        return factory.createMultipartConfig();
    }

}
