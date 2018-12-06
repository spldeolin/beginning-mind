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
 * 优先级：RequestTrackFilter的内侧
 *
 * 前置：从RequestTrack获取insignia，存入Log MDC
 *
 * 后置：清空Log MDC
 *
 * @author Deolin 2018/12/06
 */
@Order(LogMdcFilter.ORDER)
@Component
@Log4j2
public class LogMdcFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + RequestTrackFilter.ORDER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("start");
        filterChain.doFilter(request, response);
        log.info("over");
    }

}
