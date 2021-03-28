package com.spldeolin.beginningmind.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 全局线程池配置
 *
 * @author Deolin 2018/07/30
 */
@Configuration
public class ThreadPoolConfig {

    @Value("${thread-pool.core-size}")
    private Integer coreSize;

    @Value("${thread-pool.maximum-size}")
    private Integer maximumSize;

    @Value("${thread-pool.queue-capacity}")
    private Integer queueCapacity;

    @Value("${thread-pool.keep-alive-seconds}")
    private Integer keepAliveSeconds;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(maximumSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
