package com.spldeolin.beginningmind.core.filter.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.Data;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Data
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
     * HTTP协议 请求动词
     */
    private String httpMethod;

    /**
     * HTTP协议 URL
     */
    private String url;

    /**
     * HTTP协议 request content
     */
    private String requestContent;

    /**
     * HTTP协议 response content
     */
    private String responseContent;

    /**
     * HTTP协议 User-Agent
     */
    private String userAgent;

    /**
     * HTTP协议 Referer
     */
    private String referer;

    /**
     * 处理本请求的handler的全限定名
     */
    private String fullName;

    /**
     * 耗时
     */
    private Long elapsed;

    /**
     * 这次请求调用mapper中方法的信息一览（异步调用不会被记录）
     */
    private List<MappedCallDTO> mapperCalls;

    /**
     * 登录者用户ID
     */
    private Long signerId;

    /**
     * 请求者IP
     */
    private String ip;

    /**
     * 请求方法
     */
    @JsonIgnore
    @Transient
    private transient Method method;

    /**
     * 请求方法的参数名
     */
    @JsonIgnore
    @Transient
    private transient String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    @JsonIgnore
    @Transient
    private transient Object[] parameterValues;

    /**
     * 请求计时
     */
    @JsonIgnore
    @Transient
    private transient Stopwatch stopwatch;

    private static final long serialVersionUID = 1L;

    public RequestTrackDTO() {
        insignia = StringRandomUtils.generateLegibleEnNum(6);
        requestedAt = LocalDateTime.now();
        stopwatch = Stopwatch.createStarted();
        mapperCalls = Lists.newLinkedList();
    }

}