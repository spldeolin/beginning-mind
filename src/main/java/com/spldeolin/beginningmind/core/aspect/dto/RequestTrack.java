package com.spldeolin.beginningmind.core.aspect.dto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 通过切面（AnalyzeAspect）解析出的控制器信息
 *
 * @author Deolin
 */
@Data
@Accessors(chain = true)
public class RequestTrack {

    /**
     * 请求方法
     */
    private Method method;

    /**
     * 请求方法的参数名
     */
    private String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    private Object[] parameterValues;

    private Document document;

    @Data
    @org.springframework.data.mongodb.core.mapping.Document(collection = "request_track")
    public static class Document {

        /**
         * 标识
         */
        String insignia;

        /**
         * 插入时间
         */
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
        private String javaController;

        /**
         * [Java] 请求方法名
         */
        private String javaMethod;

        /**
         * [Java] 请求方法参数
         */
        private Map<String, Object> javaParameters;

        /**
         * [Java] 请求方法返回值
         */
        private Object javaReturn;

        /**
         * [Java] 请求执行的毫秒数
         */
        private Long processingMilliseconds;

    }

}
