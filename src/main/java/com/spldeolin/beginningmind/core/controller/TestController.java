package com.spldeolin.beginningmind.core.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}