package com.spldeolin.beginningmind.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.extension.exception.NotWebRequestThreadException;

/**
 * Web请求的上下文
 *
 * 基于ThreadLocal，用于静态获取当前请求的请求轨迹、Http Request、Http Response和Http Session
 *
 * @author Deolin 2018/12/01
 */
public class WebContext {

    private static final ThreadLocal<RequestTrack> requestTrackCtx = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestCtx = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletResponse> responseCtx = new ThreadLocal<>();

    private static final ThreadLocal<HttpSession> sessionCtx = new ThreadLocal<>();

    private WebContext() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    public static void setRequestTrack(RequestTrack requestTrackCtx) {
        WebContext.requestTrackCtx.set(requestTrackCtx);
    }

    public static RequestTrack getRequestTrack() {
        return requestTrackCtx.get();
    }

    public static void removeRequestTrack() {
        requestTrackCtx.remove();
    }

    public static void setRequest(HttpServletRequest request) {
        WebContext.requestCtx.set(request);
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = WebContext.requestCtx.get();
        if (request == null) {
            throw new NotWebRequestThreadException();
        }
        return request;
    }

    public static void removeRequest() {
        requestCtx.remove();
    }

    public static void setResponse(HttpServletResponse response) {
        responseCtx.set(response);
    }

    public static HttpServletResponse getResponse() throws NotWebRequestThreadException {
        HttpServletResponse response = responseCtx.get();
        if (response == null) {
            throw new NotWebRequestThreadException();
        }
        return response;
    }

    public static void removeResponse() {
        responseCtx.remove();
    }

    public static void setSession(HttpSession session) {
        sessionCtx.set(session);
    }

    public static HttpSession getSession() {
        HttpSession session = sessionCtx.get();
        if (session == null) {
            throw new NotWebRequestThreadException();
        }
        return session;
    }

    public static void removeSession() {
        sessionCtx.remove();
    }

}
