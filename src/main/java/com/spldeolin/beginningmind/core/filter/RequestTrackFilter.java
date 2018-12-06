package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.log4j.Log4j2;

/**
 * 优先级：最外层
 *
 * 前置：生成RequestTrackDTO对象，并存入ThreadLocal
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("start");
        filterChain.doFilter(request, response);
        log.info("over");
    }

}
