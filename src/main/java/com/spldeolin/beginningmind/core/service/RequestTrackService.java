package com.spldeolin.beginningmind.core.service;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
public interface RequestTrackService {

    void fillJoinPointInfo(RequestTrackDTO track, JoinPoint joinPoint);

    void asyncCompleteAndSave(RequestTrackDTO track, ContentCachingRequestWrapper wrappedRequest,
            ContentCachingResponseWrapper wrappedResponse);

}