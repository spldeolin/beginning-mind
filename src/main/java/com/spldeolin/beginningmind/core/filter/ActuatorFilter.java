package com.spldeolin.beginningmind.core.filter;

import java.io.IOException;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.util.Jsons;
import lombok.extern.log4j.Log4j2;

/**
 * 过滤器：统一返回值包装
 *
 * @author Deolin 2018/06/17
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Log4j2
public class ActuatorFilter extends OncePerRequestFilter {

    @Autowired
    private WebEndpointProperties webEndpointProperties;

    @Autowired
    private CoreProperties coreProperties;

    private String token;

    @PostConstruct
    public void init() {
        if (enableAuth()) {
            token = UUID.randomUUID().toString();
            log.info("Actuator Filter Token : " + token);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (enableAuth()) {
            if (isActuatorRequest(request)) {
                if (isTokenIncorrect(request)) {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(Jsons.toJson(RequestResult.failure(ResultCode.FORBIDDEN)));
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
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
