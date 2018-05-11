package com.spldeolin.beginningmind.filter;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.controller.FailureController;

/**
 * 过滤器：所有http404的请求，都会转发到一个RestController
 * <pre>
 * 通过RequestMappingHandlerMapping，获取容器中所有的请求Mapping，
 * 如果进入的请求匹配不到Mapping，那么将请求转发到一个返回错误码的控制器
 * </pre>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotFoundFilter implements Filter {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private BeginningMindProperties properties;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String requestUrl = request.getRequestURI();
        // 放行静态资源请求
        if (requestUrl.startsWith(properties.getFile().getMapping())) {
            chain.doFilter(req, res);
            return;
        }
        // 匹配
        boolean findable = false;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : requestMappingHandlerMapping.getHandlerMethods()
                .entrySet()) {
            RequestMappingInfo info = entry.getKey();
            if (null != info.getMatchingCondition((HttpServletRequest) req)) {
                findable = true;
            }
        }
        // 匹配不到
        if (!findable) {
            String forwardUrl = FailureController.NOT_FOUND_MAPPING +
                    "?" + FailureController.NOT_FOUND_PARAM_METHOD + "=" + request.getMethod() +
                    "&" + FailureController.NOT_FOUND_PARAM_URL + "=" + requestUrl;

            req.getRequestDispatcher(forwardUrl).forward(req, res);
        } else {
            // 放行匹配到的请求
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }

}
