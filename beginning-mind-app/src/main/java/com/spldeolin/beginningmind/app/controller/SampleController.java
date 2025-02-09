package com.spldeolin.beginningmind.app.controller;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.dto.req.SampleReqDto;
import com.spldeolin.beginningmind.api.dto.resp.SampleRespDto;
import com.spldeolin.satisficing.api.RequestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2025-02-09
 */
@RestController
@Slf4j
public class SampleController {

    @PostMapping("sampleMethod")
    public RequestResult<SampleRespDto> sampleMethod(@RequestBody @Valid SampleReqDto req) {
        SampleRespDto retval = new SampleRespDto();
        retval.setGreeting("Hello, " + req.getName());
        return RequestResult.success(retval);
    }

}