package com.spldeolin.beginningmind.aspect;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import com.spldeolin.beginningmind.aspect.dto.ControllerInfo;
import com.spldeolin.beginningmind.util.RequestContextUtils;
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
        String url = event.getRequestUrl();
        // 忽略未经过切面解析的请求
        ControllerInfo controllerInfo = RequestContextUtils.getControllerInfo();
        if (controllerInfo == null) {
            return;
        }
        // 打印请求的执行时间
        log.info("[" + event.getMethod() + "]" + event.getRequestUrl() + SEP +
                controllerInfo.getInsignia() + SEP + event.getProcessingTimeMillis());
    }

}
