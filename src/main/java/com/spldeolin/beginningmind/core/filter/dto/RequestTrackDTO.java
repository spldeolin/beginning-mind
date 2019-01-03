package com.spldeolin.beginningmind.core.filter.dto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.Jsons;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import com.spldeolin.beginningmind.core.util.Times;
import lombok.Data;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Data
public class RequestTrackDTO {

    private static final String br = System.getProperty("line.separator");

    private static final String sp = "    ";

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
     * 处理本请求的控制层请求方法的全限定名
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
    private Long userId;

    /**
     * 登录者用户名
     */
    private String userName;

    /**
     * 登录者用户手机
     */
    private String userMobile;

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
     * 请求方法
     */
    @JsonIgnore
    private transient Method method;

    /**
     * 请求方法的参数名
     */
    @JsonIgnore
    private transient String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    @JsonIgnore
    private transient Object[] parameterValues;

    /**
     * 请求计时
     */
    @JsonIgnore
    private transient Stopwatch stopwatch;

    public RequestTrackDTO() {
        insignia = StringRandomUtils.generateLegibleEnNum(6);
        requestedAt = LocalDateTime.now();
        stopwatch = Stopwatch.createStarted();
        mapperCalls = Lists.newLinkedList();
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder(128);
        result.append("RequestTrackDTO {").append(br);
        result.append(sp).append("insignia = ").append(insignia).append(br);
        result.append(sp).append("requestedAt = ").append(Times.toString(requestedAt, "yyyy-MM-dd HH:mm:ss.SSS"))
                .append(br);
        result.append(sp).append("httpMethod = ").append(httpMethod).append(br);
        result.append(sp).append("url = ").append(url).append(br);
        result.append(sp).append("requestContent = ").append(Jsons.compress(requestContent)).append(br);
        result.append(sp).append("responseContent = ").append(responseContent).append(br);
        result.append(sp).append("fullName = ").append(fullName).append(br);
        result.append(sp).append("elapsed = ").append(elapsed).append(br);
        appendMapperCalls(result, mapperCalls);
        result.append(sp).append("userId = ").append(userId).append(br);
        result.append(sp).append("userName = ").append(userName).append(br);
        result.append(sp).append("userMobile = ").append(userMobile).append(br);
        result.append(sp).append("ip = ").append(ip).append(br);
        result.append(sp).append("sessionId = ").append(sessionId).append(br);
        result.append(sp).append("activeProfile = ").append(activeProfile).append(br);
        result.append("}");

        return result.toString();
    }

    private void appendMapperCalls(StringBuilder sb, List<MappedCallDTO> mapperCalls) {
        sb.append(sp).append("mapperCalls = ").append(br);
        String sp2 = sp + sp;
        String sp3 = sp2 + sp;
        for (MappedCallDTO dto : mapperCalls) {
            sb.append(sp2).append("{").append(br);
            sb.append(sp3).append("target = ").append(dto.getTarget()).append(br);
            sb.append(sp3).append("elapsed = ").append(dto.getElapsed()).append(br);
            sb.append(sp2).append("}").append(br);
        }
    }

}