package com.spldeolin.beginningmind.core.filter.dto;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import com.google.common.base.Stopwatch;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import com.spldeolin.beginningmind.core.util.Times;
import lombok.Data;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Data
public class RequestTrack implements Serializable {

    /**
     * 请求标识
     */
    private String insignia;

    /**
     * 收到请求的时间
     */
    private LocalDateTime requestedAt;

    /**
     * HTTP协议 URL
     */
    private String url;

    /**
     * HTTP协议 请求动词
     */
    private String httpMethod;

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
    private String fullyQualifiedName;

    /**
     * 耗时
     */
    private Long elapsedMilliseconds;

    /**
     * 这次请求调用Mapper中方法的次数
     */
    private Integer mapperCalledTimes;

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
    private transient Method method;

    /**
     * 请求方法的参数名
     */
    private transient String[] parameterNames;

    /**
     * 请求方法的参数值
     */
    private transient Object[] parameterValues;

    /**
     * 请求计时
     */
    private transient Stopwatch stopwatch;

    private static final long serialVersionUID = 1L;

    public RequestTrack() {
        insignia = StringRandomUtils.generateLegibleEnNum(6);
        requestedAt = LocalDateTime.now();
        stopwatch = Stopwatch.createStarted();
        mapperCalledTimes = 0;
    }

    @Override
    public String toString() {
        String br = System.getProperty("line.separator");
        return "RequestTrackDTO {" + br +
                "    insignia = " + insignia + br +
                "    requestedAt = " + Times.toString(requestedAt, "yyyy-MM-dd HH:mm:ss.SSS") + br +
                "    url = " + url + br +
                "    httpMethod = " + httpMethod + br +
                "    requestContent = " + requestContent + br +
                "    responseContent = " + responseContent + br +
                "    fullyQualifiedName = " + fullyQualifiedName + br +
                "    elapsedMilliseconds = " + elapsedMilliseconds + br +
                "    mapperCalledTimes = " + mapperCalledTimes + br +
                "    userId = " + userId + br +
                "    userName = " + userName + br +
                "    userMobile = " + userMobile + br +
                "    ip = " + ip + br +
                "    sessionId = " + sessionId + br +
                "    activeProfile = " + activeProfile + br +
                "}";
    }

}