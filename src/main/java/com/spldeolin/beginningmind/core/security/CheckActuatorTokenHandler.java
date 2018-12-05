package com.spldeolin.beginningmind.core.security;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.stereotype.Component;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.security.exception.ActuatorTokenIncorrectException;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/12/05
 */
@Component
@Log4j2
public class CheckActuatorTokenHandler {

    @Autowired
    private WebEndpointProperties webEndpointProperties;

    @Autowired
    private CoreProperties coreProperties;

    private String token;

    @PostConstruct
    public void init() {
        if (enableAuth()) {
            token = UUID.randomUUID().toString();
            log.info("Actuator token: " + token);
        }
    }

    public void ensureTokenCorrect(HttpServletRequest request) {
        if (enableAuth()) {
            if (isActuatorRequest(request)) {
                if (isTokenIncorrect(request)) {
                    throw new ActuatorTokenIncorrectException("Token错误");
                }
            }
        }
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
