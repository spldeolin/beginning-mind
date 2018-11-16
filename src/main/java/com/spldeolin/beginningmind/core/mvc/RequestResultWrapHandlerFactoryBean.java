package com.spldeolin.beginningmind.core.mvc;

import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.Nulls;

@Component
public class RequestResultWrapHandlerFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = Lists
                .newArrayList(Nulls.toEmpty(adapter.getReturnValueHandlers()));
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