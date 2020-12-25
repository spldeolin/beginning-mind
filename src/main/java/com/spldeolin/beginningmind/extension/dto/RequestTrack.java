package com.spldeolin.beginningmind.extension.dto;

import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Data
@Builder
public class RequestTrack {

    public static final ThreadLocal<RequestTrack> CURRENT = new TransmittableThreadLocal<>();

    /**
     * insignia
     */
    private final String insignia;

    /**
     * 请求达到时间
     */
    private final LocalDateTime requestArrivedAt;

    /**
     * e.g.: POST
     */
    private final String httpMethod;

    /**
     * e.g.: /user/createUser
     */
    private final String uri;

    /**
     * 完整的URL
     */
    private final String fullUrl;

    private final HttpServletRequest rawRequest;

    private final HttpServletResponse rawResponse;

    /**
     * 更多信息，用于作为内层的过滤器、拦截器、切面、Handle的上下文
     */
    private final Map<String, String> more = Maps.newHashMap();

}