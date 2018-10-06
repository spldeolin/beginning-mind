package com.spldeolin.beginningmind.core.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.spldeolin.beginningmind.core.CoreProperties;

/**
 * 开启异步任务
 *
 * @author Deolin 2018/07/30
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Autowired
    private CoreProperties coreProperties;

    @Bean
    public TaskExecutor taskExecutor() {
        CoreProperties.TaskExecutor props = coreProperties.getTaskExecutor();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(props.getCoreSize());
        executor.setMaxPoolSize(props.getMaximumSize());
        executor.setQueueCapacity(props.getQueueCapacity());
        executor.setKeepAliveSeconds(props.getKeepAliveSeconds());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
