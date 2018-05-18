package com.spldeolin.beginningmind.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spldeolin.beginningmind.aspect.exception.RequestNotFoundException;
import com.spldeolin.beginningmind.security.exception.UnsignedException;
import lombok.extern.log4j.Log4j2;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 控制器：提供URL转发到统一异常处理的映射
 */
@Controller
@RequestMapping
@ApiIgnore
@Log4j2
public class UrlForwardToExceptionController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    public static final String SHIROFILTER_LOGINURL_URL = "/unsigned";

    public static final String UNAUTHORIZED_URL = "/unauth";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * 结合被重写的getErrorPath方法，原先所有的“Whitelabel Error Page”都会转发给这个请求方法，
     * 这个请求方法会参照BasicErrorController，解析request后，转发给统一异常处理。
     *
     * @see DefaultErrorAttributes#addStatus(java.util.Map, org.springframework.web.context.request.RequestAttributes)
     * @see DefaultErrorAttributes#getError(org.springframework.web.context.request.RequestAttributes)
     */
    @RequestMapping(ERROR_PATH)
    public void error(HttpServletRequest request) throws Throwable {
        int status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == HttpStatus.NOT_FOUND.value()) {
            throw new RequestNotFoundException();
        } else {
            Throwable throwable = (Throwable) request.getAttribute(
                    "org.springframework.boot.autoconfigure.web.DefaultErrorAttributes.ERROR");
            if (throwable == null) {
                throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
            }
            throw throwable;
        }
    }

    /**
     * 直接转发
     */
    @RequestMapping(SHIROFILTER_LOGINURL_URL)
    public void unsigned() {
        throw new UnsignedException();
    }

    /**
     * 直接转发
     */
    @RequestMapping(UNAUTHORIZED_URL)
    public void unauth() {
        throw new AuthorizationException();
    }

}
