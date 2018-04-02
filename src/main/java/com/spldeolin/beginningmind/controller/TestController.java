package com.spldeolin.beginningmind.controller;

import javax.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.cadeau.library.cache.RedisCache;
import com.spldeolin.cadeau.library.dto.RequestResult;
import com.spldeolin.cadeau.library.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("test")
@Log4j2
@Validated
public class TestController {

    @Autowired
    private RedisCache redisCache;

    @GetMapping("one")
    public RequestResult one(@RequestParam @Max(10) Integer one) {
        return RequestResult.success(one);
    }

    @GetMapping("redis")
    public RequestResult redis() {
        redisCache.setCache("ffff", "啊啊");
        return RequestResult.success();
    }

    @GetMapping("set_ses")
    public RequestResult setSes() {
        RequestContextUtil.session().setAttribute("cookie-in-session", "会话中的曲奇饼干");
        return RequestResult.success();
    }

    @GetMapping("get_ses")
    public RequestResult getSes() {
        return RequestResult.success(RequestContextUtil.session().getAttribute("cookie-in-session"));
    }

}
