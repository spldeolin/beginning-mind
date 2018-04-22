package com.spldeolin.beginningmind.controller;

import com.spldeolin.cadeau.library.dto.RequestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

    @GetMapping("unauthc")
    public RequestResult unauthc() {
        return RequestResult.failture(401, "未登录或登录超时");
    }

    @GetMapping("unauth")
    public RequestResult unauth() {
        return RequestResult.failture(403, "没有权限");
    }

}
