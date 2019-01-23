package com.spldeolin.beginningmind.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrackDTO;

/**
 * Web请求的上下文
 *
 * 基于ThreadLocal，用于静态获取当前请求的请求轨迹、request、response和session
 *
 * 获取失败，当前线程并不是Web请求线程，调用getXXX方法时将会抛出异常
 *
 * @author Deolin 2018/12/01
 */
public class WebContext {

    private static final ThreadLocal<RequestTrackDTO> REQUEST_TRACK = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletResponse> RESPONSE = new ThreadLocal<>();

    private static final ThreadLocal<HttpSession> SESSION = new ThreadLocal<>();

    public static void setRequestTrack(RequestTrackDTO track) {
        REQUEST_TRACK.set(track);
    }

    public static RequestTrackDTO getRequestTrack() {
        RequestTrackDTO track = REQUEST_TRACK.get();
        if (track == null) {
            return null;
        }
        return track;
    }

    public static void removeRequestTrack() {
        REQUEST_TRACK.remove();
    }

    public static void setRequest(HttpServletRequest request) {
        REQUEST.set(request);
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = REQUEST.get();
        if (request == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }
        return request;
    }

    public static void removeRequest() {
        REQUEST.remove();
    }

    public static void setResponse(HttpServletResponse response) {
        RESPONSE.set(response);
    }

    public static HttpServletResponse getResponse() {
        HttpServletResponse response = RESPONSE.get();
        if (response == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }
        return response;
    }

    public static void removeResponse() {
        RESPONSE.remove();
    }

    public static void setSession(HttpSession session) {
        SESSION.set(session);
    }

    public static HttpSession getSession() {
        HttpSession session = SESSION.get();
        if (session == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }
        return session;
    }

    public static void removeSession() {
        SESSION.remove();
    }

}
