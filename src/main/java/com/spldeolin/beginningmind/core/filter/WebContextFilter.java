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
import com.spldeolin.beginningmind.core.filter.async.RequestTrackAsyncHandler;
import com.spldeolin.beginningmind.core.filter.dto.RequestTrack;
import com.spldeolin.beginningmind.core.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：最外层
 *
 * 前置：将request、response、session、新构造的请求轨迹存入ThreadLocal，insignia存入headers
 *
 * 后置：补全并保存RequestTrackDTO对象（异步），清空ThreadLocal
 *
 * @author Deolin 2018/12/06
 */
@Order(WebContextFilter.ORDER)
@Component
@Log4j2
public class WebContextFilter extends OncePerRequestFilter {

    /**
     * 最外层过滤器Order数字
     */
    public static final int ORDER = 1;

    @Autowired
    private RequestTrackAsyncHandler requestTrackAsyncHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (uri.equals("/favicon.ico") || uri.equals("/")) {
            return;
        }

        // 将request、response、session、新构造的请求轨迹存入ThreadLocal
        WebContext.setRequestTrack(new RequestTrack());
        WebContext.setRequest(request);
        WebContext.setResponse(response);
        WebContext.setSession(request.getSession());

        // insignia存入headers
        response.setHeader("insignia", WebContext.getInsignia());

        filterChain.doFilter(request, response);

        // 补全并保存RequestTrackDTO对象（异步）
        requestTrackAsyncHandler.asyncCompleteAndSave(WebContext.getRequestTrack(), request);

        // 清空ThreadLocal
        WebContext.removeRequestTrack();
        WebContext.removeRequest();
        WebContext.removeResponse();
        WebContext.removeSession();
    }

}
