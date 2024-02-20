package com.spldeolin.beginningmind.app.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import javax.validation.Valid;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.api.common.RequestResult;
import com.spldeolin.beginningmind.api.javabean.req.DemoReqDto;
import com.spldeolin.beginningmind.api.javabean.resp.DemoRespDto;
import brave.Tracer;
import lombok.extern.slf4j.Slf4j;

/**
 * doc-ignore
 *
 * @author Deolin 2021-03-28
 */
@RestController
@Slf4j
public class TestController {

    //    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private Tracer tracer;

    @Autowired
    private ExecutorService threadPoolExecutor;

    @GetMapping("lock")
    public void lock() {
        String key = "goods" + 1001L;

        RLock lock = redissonClient.getLock(key);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        }

        if (isLocked) {
            log.info("aaaa");
        }

        log.info("结束");
    }

    @GetMapping("set")
    public void set() {
        String key = "aaa";
        RBucket<String> bucket = redissonClient.getBucket(key);
        log.info(bucket.get());
        bucket.set("aaaa啊啊", 2, TimeUnit.SECONDS);
        log.info(bucket.get());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        log.info(bucket.get());
        bucket.set("汉字");
    }

    @PostMapping("/demoMethod")
    public RequestResult<DemoRespDto> demoMethod(@RequestBody @Valid DemoReqDto req) {
        String traceId = tracer.currentSpan().context().traceIdString();
        String spanId = tracer.currentSpan().context().spanIdString();
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // see org.springframework.cloud.sleuth.instrument.async.ExecutorBeanPostProcessor
                // .postProcessAfterInitialization
                // https://www.cxyzjd.com/article/lovepeacee/111564045
                log.info("traceId={}", tracer.currentSpan().context().traceIdString());
                log.info("spanId={}", tracer.currentSpan().context().spanId());
            }
        });
        DemoRespDto result = new DemoRespDto().setName("汉字" + traceId + "   " + spanId);
        return RequestResult.success(result);
    }

}