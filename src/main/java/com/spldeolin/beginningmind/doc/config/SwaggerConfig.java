package com.spldeolin.beginningmind.doc.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    // @formatter:off
    @Bean
    public Docket defaultDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("default") // /v2/api-docs?group=
                .useDefaultResponseMessages(false) // "Responses"下只显示Code为200的情况
                .globalOperationParameters(commonHeaders()) // 指定通用headers
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(Date.class, String.class)
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