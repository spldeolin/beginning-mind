package com.spldeolin.beginningmind.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.spldeolin.beginningmind.core.CoreProperties;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 *
 * @author Deolin
 */
@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(coreProperties.getEnableSwagger())    // 启用/禁用开关
                .apiInfo(new ApiInfoBuilder().title("Beginning Mind").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spldeolin.beginningmind.core"))
                .paths(PathSelectors.any())
                .build();
    }

}