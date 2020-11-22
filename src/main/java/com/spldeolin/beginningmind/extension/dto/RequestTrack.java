package com.spldeolin.beginningmind.extension.dto;

import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spldeolin.beginningmind.util.JsonUtils;
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

    private static final ThreadLocal<RequestTrack> context = TransmittableThreadLocal.withInitial(RequestTrack::new);

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

    private String httpMethod;

    private String httpUrl;

    private Map<String, String> requestHeaders;

    private Map<String, String> responseHeaders;

    private String requestBody;

    private Object boundRequestBody;

    private String responseBody;

    /**
     * 耗时
     */
    private Long elapsed;

    /**
     * 请求者IP
     */
    private String ip;

    @JsonIgnore
    private transient HttpServletRequest request;

    @JsonIgnore
    private transient HttpServletResponse response;

    public void report() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append("请求到达").append("\n");
        sb.append("HTTP Request").append("\n");
        sb.append("\t").append(convertToCurl()).append("\n");
        sb.append("HTTP Response").append("\n");
        sb.append("\t").append("headers=").append(responseHeaders).append(" body=").append(responseBody).append("\n");
        sb.append("More").append("\n");
        sb.append("\t").append("elapsed=").append(elapsed).append(" ip=").append(ip).append("\n");
        sb.append("\t").append("boundRequestBody=").append(JsonUtils.toJson(boundRequestBody)).append("\n");
        log.info(sb.toString());
    }

    private String convertToCurl() {
        final StringBuilder curl = new StringBuilder(1024);
        curl.append("curl ");
        curl.append("--location ").append("--request ");
        curl.append(httpMethod).append(" ");
        curl.append("'").append(httpUrl).append("' ");
        requestHeaders.forEach((k, v) -> {
            if ("content-length".equals(k)) {
                return;
            }
            curl.append("--header '").append(k).append(": ").append(v).append("' ");
        });
        String rawJson;
        try {
            rawJson = JsonUtils.toJson(JsonUtils.toObject(requestBody, Map.class));
        } catch (Exception e) {
            rawJson = requestBody;
        }
        curl.append("--data-raw '").append(rawJson).append("'");
        return curl.toString();
    }

}