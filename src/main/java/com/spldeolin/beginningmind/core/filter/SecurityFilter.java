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
 * 优先级：LogMdcFilter的内侧
 *
 * 前置：Actuator请求的token校验 -> 是否被请离校验 -> 是否登录校验 -> 鉴权校验 -> 填入登录者信息
 *
 * 后置：无
 *
 * @author Deolin 2018/12/06
 */
@Order(SecurityFilter.ORDER)
@Component
@Log4j2
public class SecurityFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + LogMdcFilter.ORDER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("start");
        filterChain.doFilter(request, response);
        log.info("over");
    }

}
