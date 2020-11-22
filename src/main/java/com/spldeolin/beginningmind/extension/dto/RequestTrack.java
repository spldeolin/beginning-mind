package com.spldeolin.beginningmind.extension.dto;

import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
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

    private String httpMethod;

    private String httpUrl;

    private Map<String, String> requestHeaders;

    private Map<String, String> responseHeaders;

    private String requestBody;

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

    public void log() {
        String curl = convertToCurl();
        log.info("请求到达\nHTTP Request\n\t{}\nHTTP Response\n\theaders={} body={}\nMore\n\telapsed={}ms ip={}", curl,
                responseHeaders, responseBody, elapsed, ip);
    }

    @NotNull
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