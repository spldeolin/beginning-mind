package com.spldeolin.beginningmind.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.util.RequestContextUtil;

@RestController
public class RedirectController implements ErrorController {

    public static final String NOT_FOUND_MAPPING = "/error";

    @GetMapping("unauthc")
    public RequestResult unauthc() {
        return RequestResult.failure(ResultCode.UNAUTHENTICATED);
    }

    @RequestMapping(NOT_FOUND_MAPPING)
    public RequestResult notFound() {
        // 特殊对待HTTP404以外的错误
        int status = (int) RequestContextUtil.request().getAttribute("{HTTP_STATUS_CODE}");
        if (status != 404) {
            return RequestResult.failure(ResultCode.INTERNAL_ERROR, "FRAMEWORK ERROR!");
        }
        return RequestResult.failure(ResultCode.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return NOT_FOUND_MAPPING;
    }

}
