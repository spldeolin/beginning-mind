package com.spldeolin.beginningmind.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.mongodb.core.mapping.Document;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.aspect.dto.ControllerInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/07/28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Document(collection = "request_log")
public class RequestLog implements Serializable {

    /**
     * 标识
     */
    private String insignia;

    /**
     * 日志记录时间
     */
    private LocalDateTime loggedAt;

    /**
     * [协议] URl
     */
    private String httpUrl;

    /**
     * [协议] 动词
     */
    private String httpMethod;

    /**
     * [Java] 控制器类名
     */
    private String javaController;

    /**
     * [Java] 请求方法名
     */
    private String javaRequestMethod;

    /**
     * [Java] 请求方法参数
     */
    private Map<String, Object> javaParameters;

    /**
     * [Java] 请求方法返回值
     */
    private Object javaReturn;

    /**
     * [Java] 请求执行的毫秒数
     */
    private Long processingMilliseconds;

    private static final long serialVersionUID = 1L;

    public RequestLog wireControllerInfo(ControllerInfo controllerInfo) {
        insignia = controllerInfo.getInsignia();
        javaController = controllerInfo.getControllerTarget().getClass().getSimpleName();
        javaRequestMethod = controllerInfo.getMethod().getName();
        javaParameters = Maps.newHashMap();
        String[] parameterNames = controllerInfo.getParameterNames();
        Object[] parameterValues = controllerInfo.getParameterValues();
        for (int i = 0; i < parameterNames.length; i++) {
            javaParameters.put(parameterNames[i], parameterValues[i]);
        }
        return this;
    }

    public RequestLog wireHttpServletRequest(HttpServletRequest httpServletRequest) {
        httpUrl = httpServletRequest.getRequestURI();
        httpMethod = httpServletRequest.getMethod();
        return this;
    }

}