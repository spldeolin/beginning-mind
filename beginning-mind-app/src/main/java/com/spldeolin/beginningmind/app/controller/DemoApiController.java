package com.spldeolin.beginningmind.app.controller;

import brave.Tracer;
import com.spldeolin.beginningmind.api.common.RequestResult;
import com.spldeolin.beginningmind.api.javabean.req.DemoReqDto;
import com.spldeolin.beginningmind.api.javabean.resp.DemoRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Deolin 2023-04-09
 */
@RestController
@Slf4j
public class DemoApiController {

    @Autowired
    private Tracer tracer;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @PostMapping("/demoMethod")
    public RequestResult<DemoRespDto> demoMethod(@RequestBody @Valid DemoReqDto req) {
        String traceId = tracer.currentSpan().context().traceIdString();
        String spanId = tracer.currentSpan().context().spanIdString();
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // see org.springframework.cloud.sleuth.instrument.async.ExecutorBeanPostProcessor.postProcessAfterInitialization
                // https://www.cxyzjd.com/article/lovepeacee/111564045
                log.info("traceId={}", tracer.currentSpan().context().traceIdString());
                log.info("spanId={}", tracer.currentSpan().context().spanId());
            }
        });
        DemoRespDto result = new DemoRespDto().setName("汉字" + traceId + "   " + spanId);
        return RequestResult.success(result);
    }

}