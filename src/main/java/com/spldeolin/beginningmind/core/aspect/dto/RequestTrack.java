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

    @Indexed
    String insignia;

    @Indexed
    private LocalDateTime insertedAt;

    private String url;

    private String httpMethod;

    private String controller;

    private String requestMethod;

    private Map<String, String> parameters;

    private Integer resultCode;

    private String resultData;

    private String resultMessage;

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
