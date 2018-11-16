package com.spldeolin.beginningmind.core.aspect;

import java.util.Map;
import org.apache.shiro.authz.AuthorizationException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import com.spldeolin.beginningmind.core.aspect.exception.BasicErrorControllerOthersException;
import com.spldeolin.beginningmind.core.aspect.exception.RequestNotFoundException;
import com.spldeolin.beginningmind.core.filter.ActuatorFilter;
import com.spldeolin.beginningmind.core.util.Requests;

/**
 * 控制层切面
 * <pre>
 * 基础处理：控制器解析、额外注解处理、日志处理
 * </pre>
 *
 * @author Deolin
 */
@Component
@Aspect
public class ErrorControllerAspect {

    /**
     * BasicErrorController中返回值为ModelAndView的@RequestMapping方法
     */
    @Pointcut("execution(org.springframework.web.servlet.ModelAndView"
            + " org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.*(..))" +
            " && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void htmlJoinpoint() {
    }

    /**
     * BasicErrorController中返回值为ResponseEntity的@RequestMapping方法
     */
    @Pointcut("execution(org.springframework.http.ResponseEntity"
            + " org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.*(..))" +
            " && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void restJoinpoint() {
    }

    @AfterReturning(value = "htmlJoinpoint()", returning = "modelAndView")
    public void htmlAspect(ModelAndView modelAndView) {
        dispatchError(modelAndView.getModel());
    }

    @AfterReturning(value = "restJoinpoint()", returning = "responseEntity")
    public void restAspect(ResponseEntity<Map<String, Object>> responseEntity) {
        dispatchError(responseEntity.getBody());
    }

    private void dispatchError(Map<String, Object> errorAttributes) {
        if (errorAttributes == null) {
            throw new BasicErrorControllerOthersException();
        } else if (404 == (int) errorAttributes.get("status")) {
            throw new RequestNotFoundException();
        } else if (Boolean.TRUE.equals(Requests.get(ActuatorFilter.TOKEN_INCORRENT_KEY))) {
            throw new AuthorizationException();
        } else {
            throw new BasicErrorControllerOthersException(errorAttributes);
        }
    }

}