package com.spldeolin.beginningmind.core.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import com.google.common.base.Stopwatch;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.RequestTrackService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
@Service
@Log4j2
public class RequestTrackServiceImpl implements RequestTrackService {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Override
    public RequestTrackDTO buildRequestTrack() {
        RequestTrackDTO track = new RequestTrackDTO();
        track.setInsignia(StringRandomUtils.generateLegibleEnNum(6));
        track.setRequestedAt(LocalDateTime.now());
        track.setStopwatch(Stopwatch.createStarted());
        return track;
    }

    @Override
    public void fillJoinPointInfo(RequestTrackDTO track, JoinPoint joinPoint) {
        Method requestMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String[] parameterNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(requestMethod);
        Object[] parameterValues = joinPoint.getArgs();
        track.setFullyQualifiedName(joinPoint.getTarget().getClass().getName() + "#" + requestMethod.getName());
        track.setMethod(requestMethod);
        track.setParameterNames(parameterNames);
        track.setParameterValues(parameterValues);
    }

    @Async
    @Override
    public void asyncCompleteAndSave(RequestTrackDTO track, ContentCachingRequestWrapper wrappedRequest,
            ContentCachingResponseWrapper wrappedResponse) {
        readAndFillContent(track, wrappedRequest, wrappedResponse);
        analysizRequestTrack(track, wrappedRequest);
        saveTrack(track);
    }

    private void saveTrack(RequestTrackDTO track) {
        log.info("rq-" + track.getInsignia() + System.getProperty("line.separator") + track);
    }

    private void readAndFillContent(RequestTrackDTO track, ContentCachingRequestWrapper wrappedRequest,
            ContentCachingResponseWrapper wrappedResponse) {
        String requestContent = null;
        String responseContent = null;
        try {
            requestContent = new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("读取requestContent失败", e);
        }
        try {
            responseContent = new String(wrappedResponse.getContentAsByteArray(),
                    wrappedResponse.getCharacterEncoding());
            wrappedResponse.copyBodyToResponse();
        } catch (IOException e) {
            log.error("读取requestContent失败", e);
        }
        track.setRequestContent(requestContent);
        track.setResponseContent(responseContent);
    }

    private void analysizRequestTrack(RequestTrackDTO track, HttpServletRequest request) {
        track.setUrl(getFullUrlFromRequest(request));

        track.setHttpMethod(request.getMethod());

        track.setElapsedMilliseconds(track.getStopwatch().elapsed(TimeUnit.MILLISECONDS));

        Long signedUserId = track.getUserId();
        if (signedUserId != null) {
            User user = userService.get(track.getUserId()).orElseThrow(() -> new RuntimeException("不存在或是已被删除"));
            track.setUserName(user.getName());
            track.setUserMobile(user.getMobile());
        }

        track.setIp(getIpFromRequest(request));

        track.setSessionId(request.getSession().getId());

        track.setActiveProfile(environment.getActiveProfiles()[0]);
    }

    private String getFullUrlFromRequest(HttpServletRequest request) {
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

    public String getIpFromRequest(HttpServletRequest request) {
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