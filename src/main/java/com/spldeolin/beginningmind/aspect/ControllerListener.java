package com.spldeolin.beginningmind.aspect;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层监听器：打印每个请求执行时间的日志（毫秒）
 *
 * @author Deolin
 */
@Component
@Log4j2
public class ControllerListener implements ApplicationListener<ServletRequestHandledEvent> {

    private static final String SEP = "\t";

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        log.info("[" + event.getMethod() + "]" + event.getRequestUrl() + SEP +
                RequestContextUtil.getControllerInfo().getInsignia() + SEP + event.getProcessingTimeMillis());
    }

}
