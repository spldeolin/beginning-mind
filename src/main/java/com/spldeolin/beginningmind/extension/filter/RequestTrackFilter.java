package com.spldeolin.beginningmind.extension.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.util.StringRandomUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求轨迹过滤器
 *
 * @author Deolin 2018/12/06
 */
@Order(1)
@Component
@Slf4j
public class RequestTrackFilter extends OncePerRequestFilter {

    /**
     * 必须与log4j2.yml的PatternLayout.pattern中的%X{insignia}占位符名相同
     */
    private static final String logMdcInsignia = "insignia";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.equals("/favicon.ico") || uri.equals("/")) {
            return;
        }
        Stopwatch stopwatch = Stopwatch.createStarted();

        // 将request、response、session、新构造的请求轨迹存入ThreadLocal
        RequestTrack track = RequestTrack.getCurrent();
        track.setInsignia(this.getInsigniaOrElseCreate(request));
        track.setRequestedAt(LocalDateTime.now());
        track.setRequest(request);
        track.setResponse(response);

        // 设置Log MDC
        MDC.put(logMdcInsignia, "[" + track.getInsignia() + "]");

        // 包装request和response
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            response.setHeader(logMdcInsignia, track.getInsignia());

            // 补全RequestTrack
            track.setHttpMethod(wrappedRequest.getMethod());
            track.setHttpUrl(this.getFullUrl(wrappedRequest));
            track.setRequestHeaders(this.getRequestHeaders(wrappedRequest));
            track.setResponseHeaders(this.getResponseHeaders(wrappedResponse));
            track.setRequestBody(this.getRequestBody(wrappedRequest));
            track.setResponseBody(this.getResponseBody(wrappedResponse));
            track.setHandlerUrl(uri);
            track.setElapsed(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            track.setIp(this.getIp(wrappedRequest));

            // 打印RequestTrack
            track.log();

            // 清空ThreadLocal
            MDC.clear();
            RequestTrack.removeCurrent();
        }
    }

    private String getInsigniaOrElseCreate(HttpServletRequest request) {
        String result = request.getHeader("insignia");
        if (result == null) {
            result = StringRandomUtils.generateEasyReadAndSpeakChar(6);
        }
        return result;
    }

    @NotNull
    private Map<String, String> getResponseHeaders(ContentCachingResponseWrapper wrappedResponse) {
        Map<String, String> responseHeaders = Maps.newHashMap();
        Collection<String> headerNames1 = wrappedResponse.getHeaderNames();
        if (headerNames1 != null) {
            for (String headerName : headerNames1) {
                responseHeaders.put(headerName, wrappedResponse.getHeader(headerName));
            }
        }
        return responseHeaders;
    }

    @NotNull
    private Map<String, String> getRequestHeaders(ContentCachingRequestWrapper wrappedRequest) {
        Map<String, String> requestHeaders = Maps.newHashMap();
        Enumeration<String> headerNames = wrappedRequest.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                requestHeaders.put(headerName, wrappedRequest.getHeader(headerName));
            }
        }
        return requestHeaders;
    }

    private String getRequestBody(ContentCachingRequestWrapper wrappedRequest) {
        try {
            return IOUtils.toString(wrappedRequest.getInputStream(), wrappedRequest.getCharacterEncoding());
        } catch (IOException e) {
            log.error("读取request body失败", e);
            return "";
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper wrappedResponse) {
        try {
            String result = IOUtils
                    .toString(wrappedResponse.getContentInputStream(), wrappedResponse.getCharacterEncoding());
            wrappedResponse.copyBodyToResponse();
            return result;
        } catch (IOException e) {
            log.error("读取response body失败", e);
            return "";
        }
    }

    private String getFullUrl(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(64);
        url.append(request.getRequestURL());
        for (Entry<String, String[]> queryValuesEachKey : request.getParameterMap().entrySet()) {
            String queryKey = queryValuesEachKey.getKey();
            for (String queryValue : queryValuesEachKey.getValue()) {
                if (queryValue != null) {
                    url.append("&");
                    url.append(queryKey);
                    url.append("=");
                    url.append(queryValue);
                }
            }
        }
        return url.toString().replaceFirst("&", "?");
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
