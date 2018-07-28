package com.spldeolin.beginningmind.core.aspect.dto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
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
    @Field("插入时间")
    private LocalDateTime insertedAt;

    @Field("请求URL")
    private String httpUrl;

    @Field("请求动词")
    private String httpMethod;

    @Field("控制器")
    private String controller;

    @Field("请求方法")
    private String requestMethod;

    @Field("参数")
    private Map<String, String> parameterObjects;

    @Field("返回值code")
    private Integer resultCode;

    @Field("返回值data")
    private String resultData;

    @Field("返回值message")
    private String resultMessage;

    @Field("请求执行时间（毫秒）")
    private Long processingMilliseconds;

    public void setRequestResult(RequestResult requestResult) {
        resultCode = requestResult.getCode();
        Object data = requestResult.getData();
        if (data != null) {
            resultData = data.toString();
        }
        resultMessage = requestResult.getMessage();
    }

}
