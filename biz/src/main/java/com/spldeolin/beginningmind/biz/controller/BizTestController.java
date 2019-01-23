package com.spldeolin.beginningmind.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.biz.service.TableAaaService;

/**
 * @author Deolin 2019-01-23
 */

@RestController
@RequestMapping("/bizTest")
@Validated
public class BizTestController {

    @Autowired
    private TableAaaService tableAaaService;

    @GetMapping("/")
    Object ln17() {

        return tableAaaService.listAll();
    }

}