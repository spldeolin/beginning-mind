package com.spldeolin.beginningmind.core.aspect.dto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通过切面（AnalyzeAspect）解析出的控制器信息
 *
 * @author Deolin
 */
@Data
@Accessors(chain = true)
@org.springframework.data.mongodb.core.mapping.Document(collection = "request_track")
public class RequestTrack {

    /**
     * 请求方法
     */
    @Transient
    private Method method;

    @Transient
    private Integer requestBodyParameterIndex;

    /**
     * 请求方法的参数名
     */
    @Transient
    private String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    @Transient
    private Object[] parameterValues;

    /**
     * 请求开始时间
     */
    @Transient
    private long processedAt;

    @Indexed
    String insignia;

    @Indexed
    private LocalDateTime requestedAt;

    @Field("URL")
    private String url;

    @Field("请求动词")
    private String httpMethod;

    @Field("控制器")
    private String controller;

    @Field("请求方法")
    private String requestMethod;

    @Field("请求体")
    private String requestBody;

    @Field("返回值")
    private String responseBody;

    @Field("请求耗时")
    private Long processingMilliseconds;

}
