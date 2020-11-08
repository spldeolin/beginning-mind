package com.spldeolin.beginningmind.extension.wrap;

import java.util.LinkedHashMap;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.spldeolin.beginningmind.enums.ResultCodeEnum;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2018/11/16
 */
@Slf4j
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

        if (is404(returnValue)) {
            log.warn("请求404");
            result = RequestResult.failure(ResultCodeEnum.BAD_REQEUST);
        }

        delegate.handleReturnValue(result, returnType, mavContainer, webRequest);
    }

    private boolean is404(Object returnValue) {
        if (returnValue instanceof LinkedHashMap) {
            Object status = ((LinkedHashMap<?, ?>) returnValue).get("status");
            return Objects.equals(status, HttpStatus.NOT_FOUND.value());
        }
        return false;
    }

    private RequestResult ensureWrapped(Object returnValue) {
        if (returnValue instanceof RequestResult) {
            return (RequestResult) returnValue;
        }
        return RequestResult.success(returnValue);
    }

}
