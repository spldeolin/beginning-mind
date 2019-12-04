package com.spldeolin.beginningmind.controller;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.entity.BizDemoEntity;
import com.spldeolin.beginningmind.service.BizDemoService;

/**
 * 测试啊啊啊啊啊啊啊
 *
 *
 * 啊啊<b>啊</b>啊啊
 *
 *
 * 阿斯顿发啊
 * <pre>
 *     你a
 * </pre>
 *
 * @author Deolin 2019-01-23
 */
@RestController
@RequestMapping(path = "/bizTest")
@Validated
public class BizTestController {

    @Autowired
    private BizDemoService bizDemoService;

    @GetMapping
    Map<Long, BizDemoEntity> ln17() {
        System.out.println(System.getProperty("java.io.tmpdir"));
        return bizDemoService.all();
    }

    @GetMapping("/aa")
    Date aa() {
        BizDemoEntity entity = new BizDemoEntity();
//        entity.setUserNumber("1");
        bizDemoService.doSomething(entity, 6L);
        return new Date();
    }

}