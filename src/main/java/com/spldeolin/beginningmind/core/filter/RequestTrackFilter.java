package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spldeolin.beginningmind.core.filter.async.RequestTrackFilterPostHandler;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrack;
import com.spldeolin.beginningmind.core.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：最外层
 *
 * 前置：构造RequestTrackDTO对象，并存入ThreadLocal
 *
 * 后置：补全并保存RequestTrackDTO对象（异步），清空ThreadLocal
 *
 * @author Deolin 2018/12/06
 */
@Order(RequestTrackFilter.ORDER)
@Component
@Log4j2
public class RequestTrackFilter extends OncePerRequestFilter {

    /**
     * 最外层过滤器Order数字
     */
    public static final int ORDER = 1;

    @Autowired
    private RequestTrackFilterPostHandler requestTrackFilterPostHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        if (request.getRequestURI().equals("/favicon.ico")) {
            return;
        }
        RequestTrack track = new RequestTrack();
        WebContext.setRequestTrack(track);
        WebContext.setRequest(request);
        WebContext.setResponse(response);
        WebContext.setSession(request.getSession());

        filterChain.doFilter(request, response);

        requestTrackFilterPostHandler.asyncCompleteAndSave(track, request);
        WebContext.removeRequestTrack();
        WebContext.removeRequest();
        WebContext.removeResponse();
        WebContext.removeSession();
    }

}
