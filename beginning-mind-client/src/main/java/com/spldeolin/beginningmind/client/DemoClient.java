package com.spldeolin.beginningmind.client;

import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.spldeolin.beginningmind.client.javabean.req.DemoReqDto;
import com.spldeolin.beginningmind.client.javabean.resp.DemoRespDto;
import com.spldeolin.satisficing.client.RequestResult;

/**
 * @author Deolin 2023-04-09
 */
@FeignClient(value = "beginning-mind")
public interface DemoClient {

    @PostMapping("/demoMethod")
    RequestResult<DemoRespDto> demoMethod(@RequestBody @Valid DemoReqDto req);

}
