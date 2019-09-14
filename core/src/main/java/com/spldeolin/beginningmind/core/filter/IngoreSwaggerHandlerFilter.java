package com.spldeolin.beginningmind.core.filter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 继承了这个类的过滤器将会忽略所有swagger相关的请求
 *
 * @author Deolin 2019-02-26
 */
public abstract class IngoreSwaggerHandlerFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String url = request.getRequestURI();

        return url.startsWith("/swagger-resources")
                || url.startsWith("/webjars/")
                || url.startsWith("/v2/")
                || url.startsWith("/swagger-ui.html")
                || url.startsWith("/csrf");
    }

}
