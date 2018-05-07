package com.spldeolin.beginningmind.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.constant.CoupledConstant;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.util.RequestContextUtils;

@RestController
@RequestMapping("/")
public class RedirectController implements ErrorController {

    @GetMapping("/unauthc")
    public RequestResult unauthc() {
        return RequestResult.failure(ResultCode.UNAUTHENTICATED);
    }

    @RequestMapping(CoupledConstant.ERROR_PAGE_URL)
    public RequestResult notFound() {
        HttpServletRequest request = RequestContextUtils.request();
        Integer status = (Integer) request.getAttribute("{HTTP_STATUS_CODE}");
        // 浏览器直接访问/error时，直接返回
        if (status == null) {
            return RequestResult.failure(ResultCode.NOT_FOUND);
        }
        // Spring Boot捕获到未被统一异常处理捕获的异常时，
        // 会以其他的HTTP CODE进入/error请求。
        // 这种情况只会在集成涉及到访问控制的框架（如actuator未设置management.security.enabled=false）时，
        // 或者底层BUG（过滤器BUG等），才会发生。
        if (!status.equals(404)) {
            return RequestResult.failure(ResultCode.INTERNAL_ERROR, "FRAMEWORK ERROR!");
        }
        return RequestResult.failure(ResultCode.NOT_FOUND,
                "请求不存在 [" + request.getAttribute("{HTTP_METHOD}") + "]" + request.getAttribute("{HTTP_URL}"));
    }

    @Override
    public String getErrorPath() {
        return CoupledConstant.ERROR_PAGE_URL;
    }

}
