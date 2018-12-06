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
 * 优先级：SecurityFilter的内侧
 *
 * 前置：包装并替换request和response对象
 *
 * 后置：从包装对象读取content，为response包装对象调用
 *
 * @author Deolin 2018/12/06
 */
@Order(ReadContentFilter.ORDER)
@Component
@Log4j2
public class ReadContentFilter extends OncePerRequestFilter {

    public static final int ORDER = 1 + SecurityFilter.ORDER;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("start");
        filterChain.doFilter(request, response);
        log.info("over");
    }

}
