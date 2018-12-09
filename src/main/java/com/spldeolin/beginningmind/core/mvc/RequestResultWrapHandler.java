package com.spldeolin.beginningmind.core.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.util.WebContext;

/**
 * @author Deolin 2018/11/16
 */
public class RequestResultWrapHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public RequestResultWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {
        RequestResult result = ensureWrapped(returnValue);
        fillInsignia(result);

        delegate.handleReturnValue(result, returnType, mavContainer, webRequest);
    }

    private RequestResult ensureWrapped(Object returnValue) {
        if (returnValue instanceof RequestResult) {
            return (RequestResult) returnValue;
        }
        return RequestResult.success(returnValue);
    }

    private void fillInsignia(RequestResult requestResult) {
        requestResult.setInsignia(WebContext.getInsignia());
    }

}
