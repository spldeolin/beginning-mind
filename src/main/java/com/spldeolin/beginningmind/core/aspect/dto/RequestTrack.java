package com.spldeolin.beginningmind.core.aspect.dto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
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

    /**
     * 标识
     */
    @Indexed
    String insignia;

    /**
     * 插入时间
     */
    @Indexed
    private LocalDateTime insertedAt;

    /**
     * [协议] URl
     */
    private String httpUrl;

    /**
     * [协议] 动词
     */
    private String httpMethod;

    /**
     * [Java] 控制器类名
     */
    private String controller;

    /**
     * [Java] 请求方法名
     */
    private String requestMethod;

    /**
     * [Java] 请求方法参数
     */
    private Map<String, String> parameterObjects;

    /**
     * [Java] 请求方法返回值code
     */
    private Integer resultCode;

    /**
     * [Java] 请求方法返回值data
     */
    private String resultData;

    /**
     * [Java] 请求方法返回值message
     */
    private String resultMessage;

    /**
     * [Java] 请求执行的毫秒数
     */
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
