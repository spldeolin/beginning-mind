package com.spldeolin.beginningmind.core.security;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.spldeolin.beginningmind.core.security.exception.TokenIncorrectException;
import com.spldeolin.beginningmind.core.security.exception.UnauthorizeException;
import com.spldeolin.beginningmind.core.security.holder.TokenHolder;

/**
 * @author Deolin 2019-02-23
 */
@Component
public class TokenChecker {

    @Autowired
    private TokenHolder tokenHolder;

    public void ensureTokenCorrect(HttpServletRequest request, Method requestMethod) throws UnauthorizeException {
        String reqToken = request.getParameter("token");

        String mappingMethod = getMappingMethod(requestMethod);
        String mappingPath = request.getRequestURI();
        String token = tokenHolder.getToken(mappingMethod, mappingPath);
        if (token == null) {
            throw new TokenIncorrectException("没有为请求设置token值 " + mappingMethod + " " + mappingPath);
        }
        if (!token.equals(reqToken)) {
            throw new TokenIncorrectException("TOKEN不正确");
        }
    }

    private String getMappingMethod(Method requestMethod) {
        if (null != requestMethod.getAnnotation(GetMapping.class)) {
            return "get";
        }
        if (null != requestMethod.getAnnotation(PostMapping.class)) {
            return "post";
        }
        if (null != requestMethod.getAnnotation(PutMapping.class)) {
            return "put";
        }
        if (null != requestMethod.getAnnotation(DeleteMapping.class)) {
            return "delete";
        }

        RequestMapping requestMapping = requestMethod.getAnnotation(RequestMapping.class);
        if (null != requestMapping) {
            RequestMethod[] requestMethods = requestMapping.method();
            if (requestMethods.length > 0) {
                return requestMethods[0].name();
            }
        }

        return "unknown";
    }

}
