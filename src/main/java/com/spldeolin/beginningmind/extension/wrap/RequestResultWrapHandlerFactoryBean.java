package com.spldeolin.beginningmind.extension.wrap;

import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import com.google.common.collect.Lists;

/**
 * @author Deolin
 */
@Component
public class RequestResultWrapHandlerFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        if (returnValueHandlers == null) {
            returnValueHandlers = Lists.newArrayList();
        } else {
            // 原returnValueHandlers的类型是不可变的List
            returnValueHandlers = Lists.newArrayList(returnValueHandlers);
        }
        decorateHandlers(returnValueHandlers);
        adapter.setReturnValueHandlers(returnValueHandlers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                RequestResultWrapHandler decorator = new RequestResultWrapHandler(handler);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
                break;
            }
        }
    }

}