package com.spldeolin.beginningmind.filter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import springfox.documentation.swagger2.web.Swagger2Controller;

/**
 * 继承了这个类的过滤器将会忽略所有swagger相关的请求
 *
 * @author Deolin 2019-02-26
 */
public abstract class IngoreSwaggerFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return Swagger2Controller.DEFAULT_URL.equals(request.getRequestURI());
    }

}
