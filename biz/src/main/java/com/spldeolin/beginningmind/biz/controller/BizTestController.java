package com.spldeolin.beginningmind.biz.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.biz.entity.BizDemoEntity;
import com.spldeolin.beginningmind.biz.service.BizDemoService;

/**
 * @author Deolin 2019-01-23
 */

@RestController
@RequestMapping("/bizTest")
@Validated
public class BizTestController {

    @Autowired
    private BizDemoService bizDemoService;

    @GetMapping
    List<BizDemoEntity> ln17() {
        System.out.println(System.getProperty("java.io.tmpdir"));
        return bizDemoService.all();
    }

}