package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/8/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RequestTrack implements Serializable {

    /**
     * ID
     */
    @org.springframework.data.annotation.Id
    private String id;

    /**
     * 请求标识
     */
    @Indexed
    private String insignia;

    /**
     * 收到请求的时间
     */
    private LocalDateTime acceptedAt;

    /**
     * URL
     */
    private String url;

    /**
     * 请求动词
     */
    @Field("http_method")
    private String httpMethod;

    /**
     * 控制器
     */
    private String controller;

    /**
     * 请求方法
     */
    @Field("request_method")
    private String requestMethod;

    /**
     * 请求耗时
     */
    @Field("processing_milliseconds")
    private Long processingMilliseconds;

    /**
     * 登录者用户ID
     */
    @Field("user_id")
    private Long userId;

    /**
     * 登录者用户名
     */
    @Field("user_name")
    private String userName;

    /**
     * 请求者IP
     */
    private String ip;

    /**
     * 会话ID
     */
    @Field("session_id")
    private String sessionId;

    /**
     * 项目启动时指定的profile
     */
    @Field("active_profile")
    private String activeProfile;

    /**
     * 登录者用户手机
     */
    @Field("user_mobile")
    private String userMobile;

    /**
     * 请求体
     */
    @Field("request_body")
    private String requestBody;

    /**
     * 返回值
     */
    @Field("response_body")
    private String responseBody;

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

    private static final long serialVersionUID = 1L;

}