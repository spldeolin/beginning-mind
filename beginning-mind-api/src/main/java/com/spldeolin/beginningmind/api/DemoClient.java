package com.spldeolin.beginningmind.api;

import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.spldeolin.beginningmind.api.javabean.req.DemoReqDto;
import com.spldeolin.beginningmind.api.javabean.resp.DemoRespDto;
import com.spldeolin.satisficing.api.RequestResult;

/**
 * @author Deolin 2023-04-09
 */
@FeignClient(value = "beginning-mind")
public interface DemoClient {

    @PostMapping("/demoMethod")
    RequestResult<DemoRespDto> demoMethod(@RequestBody @Valid DemoReqDto req);

}
