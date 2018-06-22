/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.core.model.SecurityPermission;

/**
 * 示例
 *
 * @author Deolin 2018/06/22
 */
@RestController
@RequestMapping("/demo")
@Validated
public class DemoController {

    /**
     * 获取
     */
    @PostMapping("/create/{d}")
    SecurityPermission create(@RequestParam String a, @RequestParam(required = false) String b,
            @RequestParam(defaultValue = "") String c, @PathVariable String d) {

        return null;
    }

    @GetMapping("/get")
    String get() {
        return null;
    }

}