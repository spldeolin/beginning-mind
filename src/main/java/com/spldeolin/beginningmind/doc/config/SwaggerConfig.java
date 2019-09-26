package com.spldeolin.beginningmind.doc.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.Lists;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启用Swagger文档
 *
 * @author Deolin 2019-01-21
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    // @formatter:off
    @Bean
    public Docket defaultDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("default") // /v2/api-docs?group=
                .useDefaultResponseMessages(true) // "Responses"下只显示Code为200的情况
                .globalOperationParameters(commonHeaders()) // 指定通用headers
                .select()
                .paths(PathSelectors.ant("/**"))    // 请求url
                .build();
    }
    // @formatter:on

    private List<Parameter> commonHeaders() {
        return Lists.newArrayList(
                new ParameterBuilder().name("token").description("登录态TOKEN").modelRef(new ModelRef("string"))
                        .parameterType("header").required(false).build());
    }

}