package com.spldeolin.beginningmind.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.restful.dto.RequestResult;

@RestController
public class RedirectController implements ErrorController {

    private static final String ERROR_MAPPING = "error";

    @GetMapping("unauthc")
    public RequestResult unauthc() {
        return RequestResult.failture(ResultCode.UNAUTHORIZED, "未登录或登录超时");
    }

    @GetMapping("unauth")
    public RequestResult unauth() {
        return RequestResult.failture(ResultCode.FORBIDDEN, "没有权限");
    }

    @GetMapping(ERROR_MAPPING)
    public RequestResult notFound() {
        return RequestResult.failture(ResultCode.NOT_FOUND, "资源不存在或是已被删除");
    }

    @Override
    public String getErrorPath() {
        return ERROR_MAPPING;
    }

}
