package com.spldeolin.beginningmind.extension.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Data
@Slf4j
public class RequestTrack {

    /**
     * insignia
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
    private String httpUrl;

    /**
     * HTTP协议 request headers
     */
    private Map<String, String> requestHeaders;

    /**
     * HTTP协议 response headers
     */
    private Map<String, String> responseHeaders;

    /**
     * HTTP协议 request body
     */
    private String requestBody;

    /**
     * HTTP协议 response body
     */
    private String responseBody;

    /**
     * 请求URL
     */
    private String handlerUrl;

    /**
     * 耗时
     */
    private Long elapsed;

    /**
     * 这次请求调用mapper中方法的信息一览（异步调用不会被记录）
     */
    private List<MappedCallDTO> mapperCalls = Lists.newArrayList();

    /**
     * 登录者用户ID
     */
    private Long signerId;

    /**
     * 请求者IP
     */
    private String ip;

    public void log() {
        log.info("requestedAt={}", requestedAt);
        log.info("httpMethod={}", httpMethod);
        log.info("httpUrl={}", httpUrl);
        log.info("requestHeaders={}", requestHeaders);
        log.info("responseHeaders={}", responseHeaders);
        log.info("requestBody={}", requestBody);
        log.info("responseBody={}", responseBody);
        log.info("handlerUrl={}", handlerUrl);
        log.info("elapsed={}", elapsed);
        log.info("mapperCalls={}", mapperCalls);
        log.info("signerId={}", signerId);
        log.info("ip={}", ip);
    }

}