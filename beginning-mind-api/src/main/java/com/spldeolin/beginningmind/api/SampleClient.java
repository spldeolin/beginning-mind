package com.spldeolin.beginningmind.api;

import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.spldeolin.beginningmind.api.dto.req.SampleReqDto;
import com.spldeolin.beginningmind.api.dto.resp.SampleRespDto;
import com.spldeolin.satisficing.api.RequestResult;

/**
 * @author Deolin 2025-02-09
 */
@FeignClient(value = "beginning-mind")
public interface SampleClient {

    @PostMapping("/sampleMethod")
    RequestResult<SampleRespDto> sampleMethod(@RequestBody @Valid SampleReqDto req);

}
