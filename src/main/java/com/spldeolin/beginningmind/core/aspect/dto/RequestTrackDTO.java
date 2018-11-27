package com.spldeolin.beginningmind.core.aspect.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RequestTrackDTO implements Serializable {

    /**
     * 请求标识
     */
    private String insignia;

    /**
     * 收到请求的时间
     */
    private LocalDateTime requestedAt;

    /**
     * URL
     */
    private String url;

    /**
     * 请求动词
     */
    private String httpMethod;

    /**
     * 控制器
     */
    private String controller;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求耗时
     */
    private Long processingMilliseconds;

    /**
     * 登录者用户ID
     */
    private Long userId;

    /**
     * 登录者用户名
     */
    private String userName;

    /**
     * 请求者IP
     */
    private String ip;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 项目启动时指定的profile
     */
    private String activeProfile;

    /**
     * 登录者用户手机
     */
    private String userMobile;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 返回值
     */
    private String responseBody;

    /**
     * 请求方法
     */
    @JsonIgnore
    private Method method;

    /**
     * 请求方法的参数名
     */
    @JsonIgnore
    private String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    @JsonIgnore
    private Object[] parameterValues;

    /**
     * 请求计时
     */
    @JsonIgnore
    private Stopwatch stopwatch;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        String br = System.getProperty("line.separator");
        return "RequestTrackDTO {" + br +
                "    insignia = " + insignia + br +
                "    requestedAt = " + requestedAt + br +
                "    url = " + url + br +
                "    httpMethod = " + httpMethod + br +
                "    controller = " + controller + br +
                "    requestMethod = " + requestMethod + br +
                "    processingMilliseconds = " + processingMilliseconds + br +
                "    userId = " + userId + br +
                "    userName = " + userName + br +
                "    ip = " + ip + br +
                "    sessionId = " + sessionId + br +
                "    activeProfile = " + activeProfile + br +
                "    userMobile = " + userMobile + br +
                "    requestBody = " + requestBody + br +
                "    responseBody = " + responseBody + br +
                '}';
    }

}