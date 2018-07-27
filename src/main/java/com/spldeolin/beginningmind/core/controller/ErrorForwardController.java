/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.spldeolin.beginningmind.core.aspect.exception.RequestNotFoundException;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 控制器：提供URL转发到统一异常处理的映射
 *
 * 这里的请求不参与授权、鉴权
 *
 * @author Deolin 2018/07/27
 */
@Controller
@RequestMapping("/")
@ApiIgnore
public class ErrorForwardController implements ErrorController {

    public static final String ERROR_PATH = "/error";

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
    void error(HttpServletRequest request) throws Throwable {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        // 这种情况应该是直接访问的
        if (status == null) {
            throw new RequestNotFoundException();
        }
        if (status.equals(HttpStatus.NOT_FOUND.value())) {
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

}