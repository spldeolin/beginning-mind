package com.spldeolin.beginningmind.api;

import com.spldeolin.beginningmind.api.common.RequestResult;
import com.spldeolin.beginningmind.api.javabean.req.DemoReqDto;
import com.spldeolin.beginningmind.api.javabean.resp.DemoRespDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author Deolin 2023-04-09
 */
@FeignClient(value = "beginning-mind")
public interface DemoApi {

    @PostMapping("/demoMethod")
    RequestResult<DemoRespDto> demoMethod(@RequestBody @Valid DemoReqDto req);

}
