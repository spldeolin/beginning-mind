package com.spldeolin.beginningmind.app.controller;

import com.spldeolin.beginningmind.api.common.RequestResult;
import com.spldeolin.beginningmind.api.javabean.req.DemoReqDto;
import com.spldeolin.beginningmind.api.javabean.resp.DemoRespDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Deolin 2023-04-09
 */
@RestController
public class DemoApiController {

    @PostMapping("/demoMethod")
    public RequestResult<DemoRespDto> demoMethod(@RequestBody @Valid DemoReqDto req) {
        DemoRespDto result = new DemoRespDto().setName("汉字");
        return RequestResult.success(result);
    }

}