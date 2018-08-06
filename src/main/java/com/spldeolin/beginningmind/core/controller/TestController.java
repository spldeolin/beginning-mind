package com.spldeolin.beginningmind.core.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.controller.annotation.Authorization;

/**
 * 用于测试的控制器
 *
 * @author Deolin 2018/08/04
 */
@RestController // TODO 生产环境注释掉这个控制器
@RequestMapping(TestController.TEST_REQUEST_MAPPING_PREFIX)
@Validated
public class TestController {

    public static final String TEST_REQUEST_MAPPING_PREFIX = "/test";

    @PostMapping("/ppp")
    @Authorization(display = "普通请求", menuId = 1L)
    void ln20() {

    }

    @GetMapping("/ggg")
    @Authorization(menuId = 1L)
    Object ln28() {
        return null;
    }

    @GetMapping("/pp1p")
    @Authorization(menuId = Authorization.WHAT_EVER, mustHave = true)
    Object ln35() {

        return null;
    }

}