package com.spldeolin.beginningmind.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import lombok.extern.log4j.Log4j2;

/**
 * 控制器：单纯返回错误码
 * <pre>
 * 这个控制器往往用于被转发，或是被重定向
 * </pre>
 */
@RestController
@RequestMapping("/")
@Log4j2
public class FailureController {

    public static final String UNAUTHC_MAPPING = "/unauthc";

    public static final String NOT_FOUND_MAPPING = "/404";

    public static final String NOT_FOUND_PARAM_METHOD = "method";

    public static final String NOT_FOUND_PARAM_URL = "url";

    @GetMapping(UNAUTHC_MAPPING)
    public RequestResult unauthc() {
        return RequestResult.failure(ResultCode.UNAUTHENTICATED);
    }

    @RequestMapping(NOT_FOUND_MAPPING)
    public RequestResult notFound(@RequestParam(value = NOT_FOUND_PARAM_METHOD, required = false) String method,
            @RequestParam(value = NOT_FOUND_PARAM_URL, required = false) String url) {
        if (StringUtils.isNoneEmpty(method, url)) {
            return RequestResult.failure(ResultCode.NOT_FOUND,
                    "请求不存在。[" + getFirstPart(method) + "]" + getFirstPart(url));
        } else {
            return RequestResult.failure(ResultCode.NOT_FOUND);
        }
    }

    private String getFirstPart(String s) {
        return StringUtils.split(s, ',')[0];
    }

}
