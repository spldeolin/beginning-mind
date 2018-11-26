package com.spldeolin.beginningmind.core.service;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrackDTO;

/**
 * 请求轨迹
 *
 * @author Deolin 2018/11/15
 */
public interface RequestTrackService {

    /**
     * 设置 切点、登录者ID，生成请求轨迹
     */
    RequestTrackDTO setJoinPointAndHttpRequest(JoinPoint joinPoint, Long userId);

    /**
     * 补全请求轨迹信息，并保存请求轨迹
     */
    void completeAndSaveTrack(RequestTrackDTO track, HttpServletRequest request, Object dataObject);

    /**
     * 补全请求轨迹信息，并保存请求轨迹
     */
    void completeAndSaveTrack(RequestTrackDTO track, HttpServletRequest request, RequestResult requestResult);

}