package com.spldeolin.beginningmind.extension.dto;

import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private static final ThreadLocal<RequestTrack> context = ThreadLocal.withInitial(RequestTrack::new);

    private RequestTrack() {
    }

    public static RequestTrack getCurrent() {
        return context.get();
    }

    public static void removeCurrent() {
        context.remove();
    }

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
     * 登录者用户ID
     */
    private Long signerId;

    /**
     * 请求者IP
     */
    private String ip;

    private HttpServletRequest request;

    private HttpServletResponse response;

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
        log.info("signerId={}", signerId);
        log.info("ip={}", ip);
    }

}