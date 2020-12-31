package com.spldeolin.beginningmind.extension.reqtrack.handleimpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.extension.reqtrack.RequestTrack;
import com.spldeolin.beginningmind.extension.reqtrack.handle.ReportRequestTrackHandle;
import com.spldeolin.beginningmind.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2020-12-25
 */
@Slf4j
@Component
public class DefaultReportRequestTrackHandle implements ReportRequestTrackHandle {

    @Override
    public StringBuilder buildArrivedReport(RequestTrack requestTrack) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("requestArrived-").append(requestTrack.getUri()).append("-").append(requestTrack.getInsignia());
        return sb;
    }

    @Override
    public StringBuilder buildLeavedReport(RequestTrack requestTrack,
            ContentCachingRequestWrapper contentCachingRequestWrapper,
            ContentCachingResponseWrapper contentCachingResponseWrapper) {
        long elapsed = Duration.between(requestTrack.getRequestArrivedAt(), LocalDateTime.now()).toMillis();
        Collection<String> curlLines = convertToCurlLines(requestTrack, contentCachingRequestWrapper);
        String curl = Joiner.on("\n\t").join(curlLines);
        Map<String, String> responseHeaders = getResponseHeaders(contentCachingResponseWrapper);
        String rawResponseBody = getRawResponseBody(contentCachingResponseWrapper);

        StringBuilder sb = new StringBuilder(1024);
        sb.append("requestLeaved-").append(requestTrack.getUri()).append("-").append(requestTrack.getInsignia())
                .append("\n");
        sb.append("HTTP Request").append("\n");
        sb.append("\t").append(curl).append("\n\n");
        sb.append("HTTP Response").append("\n");
        sb.append("\t").append("headers=").append(responseHeaders).append("\n");
        sb.append("\t").append("body=").append(rawResponseBody).append("\n\n");
        sb.append("Others").append("\n");
        sb.append("\t").append("elapsed=").append(elapsed).append("\n");
        sb.append("\t").append("ip=").append(parseIp(contentCachingRequestWrapper)).append("\n");
        return sb;
    }

    @Override
    public StringBuilder buildMoreLeavedReport(Map<String, Object> more) {
        StringBuilder sb = new StringBuilder(64);
        more.forEach((k, v) -> sb.append("\t").append(k).append("=").append(v.toString()).append("\n"));
        return sb;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Map<String, String> responseHeaders = Maps.newHashMap();
        Collection<String> headerNames = response.getHeaderNames();
        if (headerNames != null) {
            for (String headerName : headerNames) {
                responseHeaders.put(headerName, response.getHeader(headerName));
            }
        }
        return responseHeaders;
    }

    private Collection<String> convertToCurlLines(RequestTrack requestTrack, ContentCachingRequestWrapper request) {
        final Collection<String> result = Lists.newArrayList();
        result.add(
                "curl --location --request " + requestTrack.getHttpMethod() + " '" + requestTrack.getFullUrl() + "'");
        String rawJson = JsonUtils.compressJson(getRawRequestBody(request));
        result.add("--data-raw '" + rawJson + "'");
        getRequestHeaders(request).forEach((k, v) -> {
            if ("content-length".equals(k)) {
                return;
            }
            result.add("--header '" + k + ": " + v + "'");
        });
        return result;
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> requestHeaders = Maps.newHashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                requestHeaders.put(headerName, request.getHeader(headerName));
            }
        }
        return requestHeaders;
    }

    private String getRawRequestBody(ContentCachingRequestWrapper wrappedRequest) {
        try {
            // Response Body由客户端提供，所以编码从报文中获取
            String encoding = wrappedRequest.getCharacterEncoding();
            String result = IOUtils.toString(wrappedRequest.getContentAsByteArray(), encoding);
            if (StringUtils.isEmpty(result)) {
                // 报文有body，但handler没有@RequestBody时，wrappedRequest.getInputStream()会有内容
                result = IOUtils.toString(wrappedRequest.getInputStream(), encoding);
            }
            return result;
        } catch (IOException e) {
            log.error("读取request body失败", e);
            return "";
        }
    }

    private String getRawResponseBody(ContentCachingResponseWrapper wrappedResponse) {
        try {
            // Response Body由服务端产生，所以编码固定是UTF-8
            String result = IOUtils.toString(wrappedResponse.getContentInputStream(), StandardCharsets.UTF_8);
            wrappedResponse.copyBodyToResponse();
            return result;
        } catch (IOException e) {
            log.error("读取response body失败", e);
            return "";
        }
    }

    private String parseIp(HttpServletRequest request) {
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