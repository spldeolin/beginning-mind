package com.spldeolin.beginningmind.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.controller.dto.RequestResult;

@RestController
public class RedirectController implements ErrorController {

    public static final String ERROR_MAPPING = "/error";

    @GetMapping("unauthc")
    public RequestResult unauthc() {
        return RequestResult.failure(ResultCode.UNAUTHENTICATED);
    }

    @RequestMapping(ERROR_MAPPING)
    public RequestResult notFound() {
        return RequestResult.failure(ResultCode.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_MAPPING;
    }

}
