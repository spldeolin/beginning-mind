package com.spldeolin.beginningmind.core.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrack;
import com.spldeolin.beginningmind.core.util.RequestContextUtils;
import com.spldeolin.beginningmind.core.util.string.StringRandomUtils;
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
    public RequestTrack analysisJoinPointAndHttpRequest(JoinPoint joinPoint) {
        HttpServletRequest request = RequestContextUtils.request();

        RequestTrack requestTrack = new RequestTrack();
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = joinPoint.getArgs();
        requestTrack.setMethod(requestMethod);
        requestTrack.setParameterNames(parameterNames);
        requestTrack.setParameterValues(parameterValues);

        Map<String, String> parameterObjects = Maps.newHashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterValues[i] != null) {
                parameterObjects.put(parameterNames[i], parameterValues[i].toString());
            }
        }
        if (parameterObjects.size() == 0) {
            parameterObjects = null;
        }
        requestTrack.setInsignia(StringRandomUtils.generateLegibleEnNum(6));
        requestTrack.setInsertedAt(LocalDateTime.now());
        requestTrack.setUrl(request.getRequestURI());
        requestTrack.setHttpMethod(request.getMethod());
        requestTrack.setController(joinPoint.getTarget().getClass().getSimpleName());
        requestTrack.setRequestMethod(requestMethod.getName());
        requestTrack.setParameters(parameterObjects);
        return requestTrack;
    }

    /**
     * （异步） 设置切点结束后的数据，并将请求轨迹存入Mongo
     */
    @Async
    public void savaToMongoAfterProcessing(RequestTrack track, Object dataObject) {
        track.setProcessingMilliseconds(System.currentTimeMillis() - track.getProcessedAt());
        track.setRequestResult(ensureRequestResult(dataObject));
        mongoTemplate.save(track);
    }

    /**
     * （异步） 设置切点异常抛出后的数据，并将请求轨迹存入Mongo
     */
    @Async
    public void saveToMongoAfterThrowing(RequestTrack track, RequestResult requestResult) {
        track.setProcessingMilliseconds(System.currentTimeMillis() - track.getProcessedAt());
        track.setRequestResult(requestResult);
        // 存入MongoDB
        mongoTemplate.save(track);
    }

    private RequestResult ensureRequestResult(Object object) {
        if (object instanceof RequestResult) {
            return (RequestResult) object;
        }
        return RequestResult.success(object);
    }

}
