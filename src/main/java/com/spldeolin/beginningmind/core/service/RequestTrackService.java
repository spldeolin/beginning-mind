package com.spldeolin.beginningmind.core.service;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
public interface RequestTrackService {

    RequestTrackDTO buildRequestTrack();

    void fillJoinPointInfo(RequestTrackDTO track, JoinPoint joinPoint);

    void fillRequestResultInfo(RequestTrackDTO track, Object requestResult);

    void completeAndSave(RequestTrackDTO track, HttpServletRequest request);

}