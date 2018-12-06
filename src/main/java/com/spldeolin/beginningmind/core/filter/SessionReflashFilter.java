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
 * 优先级：ReadContentFilter的内侧
 *
 * 前置：无
 *
 * 后置：刷新全局会话和会话每个k-v的失效时间
 *
 * @author Deolin 2018/12/06
 */
@Order(SessionReflashFilter.ORDER)
@Component
@Log4j2
public class SessionReflashFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + ReadContentFilter.ORDER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("start");
        filterChain.doFilter(request, response);
        log.info("over");
    }

}
