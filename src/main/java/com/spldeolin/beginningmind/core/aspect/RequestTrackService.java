package com.spldeolin.beginningmind.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrack;
import com.spldeolin.beginningmind.core.util.Jsons;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 处理、保存请求轨迹
 *
 * @author Deolin 2018/07/30
 */
@Service
@Log4j2
public class RequestTrackService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 解析切点与当前的Request对象
     */
    public RequestTrack setJoinPointAndHttpRequest(JoinPoint joinPoint, HttpServletRequest request) {
        RequestTrack track = new RequestTrack();
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = joinPoint.getArgs();
        track.setMethod(requestMethod);
        track.setParameterNames(parameterNames);
        track.setParameterValues(parameterValues);
        track.setInsignia(StringRandomUtils.generateLegibleEnNum(6));
        track.setRequestedAt(LocalDateTime.now());
        track.setUrl(request.getRequestURI());
        track.setHttpMethod(request.getMethod());
        track.setController(joinPoint.getTarget().getClass().getSimpleName());
        track.setRequestMethod(requestMethod.getName());

        Parameter[] parameters = requestMethod.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (isRequestBody(parameters[i])) {
                track.setRequestBodyParameterIndex(i);
            }
        }

        return track;
    }

    /**
     * （异步） 设置切点结束后的数据，并将请求轨迹存入Mongo
     */
    @Async
    @SneakyThrows
    public void savaToMongoAfterProcessing(RequestTrack track, HttpServletRequest request, Object dataObject) {
        analysizRequestTrack(track, request);

        track.setResponseBody(Jsons.toJson(ensureRequestResult(dataObject)));

        mongoTemplate.save(track);
    }

    /**
     * （异步） 设置切点异常抛出后的数据，并将请求轨迹存入Mongo
     */
    @Async
    public void saveToMongoAfterThrowing(RequestTrack track, HttpServletRequest request, RequestResult requestResult) {
        analysizRequestTrack(track, request);

        track.setResponseBody(Jsons.toJson(requestResult));

        mongoTemplate.save(track);
    }

    private void analysizRequestTrack(RequestTrack track, HttpServletRequest request) {
        StringBuilder url = new StringBuilder(64);
        url.append(track.getUrl());
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
        track.setUrl(url.toString().replaceFirst("&", "?"));

        Object requestBodyParameterValue = null;
        Annotation[][] annotations = track.getMethod().getParameterAnnotations();
        outter:
        for (int parameterIndex = 0; parameterIndex < annotations.length; parameterIndex++) {
            Annotation[] annotationsEachParameter = annotations[parameterIndex];
            for (Annotation annotation : annotationsEachParameter) {
                if (annotation instanceof RequestBody) {
                    requestBodyParameterValue = track.getParameterValues()[parameterIndex];
                    break outter;
                }
            }
        }
        if (requestBodyParameterValue != null) {
            track.setRequestBody(Jsons.toJson(requestBodyParameterValue));
        }

        track.setProcessingMilliseconds(System.currentTimeMillis() - track.getProcessedAt());
    }

    private RequestResult ensureRequestResult(Object object) {
        if (object instanceof RequestResult) {
            return (RequestResult) object;
        }
        return RequestResult.success(object);
    }

    private boolean isRequestBody(Parameter parameter) {
        return hasAnnotation(parameter, RequestBody.class);
    }

    private boolean isRequestParam(Parameter parameter) {
        return hasAnnotation(parameter, RequestParam.class);
    }

    private boolean isPathVariable(Parameter parameter) {
        return hasAnnotation(parameter, PathVariable.class);
    }

    private boolean hasAnnotation(Parameter parameter, Class<? extends Annotation> annotationType) {
        for (Annotation annotation : parameter.getAnnotations()) {
            if (annotation.getClass() == annotationType) {
                return true;
            }
        }
        return false;
    }

    private String getUrlQuerySeparator(Integer... indexes) {
        boolean isAllZero = true;
        for (Integer index : indexes) {
            if (index != 0) {
                isAllZero = false;
                break;
            }
        }
        if (isAllZero) {
            return "?";
        } else {
            return "&";
        }
    }

    private String getRequestParamName(Parameter parameter) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        String assignedName = requestParam.value();
        if (StringUtils.isBlank(assignedName)) {
            assignedName = requestParam.name();
        }
        if (StringUtils.isNotBlank(assignedName)) {
            return assignedName;
        } else {
            return parameter.getName();
        }
    }

}
