package com.spldeolin.beginningmind.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import com.spldeolin.beginningmind.constant.CoupledConstant;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment environment;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable())    // 启用/禁用开关
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spldeolin.beginningmind"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @return 生产环境返回false，其他环境返回true
     */
    private boolean enable() {
        for (String activeProfile : environment.getActiveProfiles()) {
            if (CoupledConstant.PROD_PROFILE_NAME.equals(activeProfile)) {
                return false;
            }
        }
        return true;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Beginning Mind")
                .description("初心")
                .termsOfServiceUrl("https://github.com/spldeolin/beginning-mind")
                .build();
    }

}
