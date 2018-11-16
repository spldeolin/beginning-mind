package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import lombok.extern.log4j.Log4j2;

/**
 * 过滤器：统一返回值包装
 *
 * @author Deolin 2018/06/17
 */
@Component
@Log4j2
public class ActuatorFilter implements Filter {

    public static final String TOKEN_INCORRENT_KEY = "com.spldeolin.beginningmind.core.filter.ActuatorFilter.TOKEN_INCORRENT_KEY";

    @Autowired
    private WebEndpointProperties webEndpointProperties;

    @Autowired
    private CoreProperties coreProperties;

    private String token;

    @Override
    public void init(FilterConfig filterConfig) {
        if (enableAuth()) {
            token = UUID.randomUUID().toString();
            log.info("Actuator Filter Token : " + token);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (enableAuth()) {
            if (isActuatorRequest(request)) {
                if (isTokenIncorrect(request)) {
                    request.setAttribute(TOKEN_INCORRENT_KEY, true);
                    throw new ServletException();
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private boolean enableAuth() {
        return coreProperties.getEnableActuatorFilter();
    }

    private boolean isActuatorRequest(ServletRequest request) {
        return ((HttpServletRequest) request).getRequestURI().startsWith(webEndpointProperties.getBasePath());
    }

    private boolean isTokenIncorrect(ServletRequest request) {
        return !token.equals(request.getParameter("token"));
    }

}
