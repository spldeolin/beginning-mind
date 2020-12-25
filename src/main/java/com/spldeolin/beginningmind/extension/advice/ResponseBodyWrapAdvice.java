package com.spldeolin.beginningmind.extension.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.spldeolin.beginningmind.extension.javabean.RequestResult;

/**
 * 用于确保ResponseBody对象被包装在统一包装类中的ControllerAdvice
 *
 * @author Deolin 2020-11-22
 */
@RestControllerAdvice
public class ResponseBodyWrapAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (selectedContentType == MediaType.APPLICATION_JSON
                || selectedContentType == MediaType.APPLICATION_JSON_UTF8) {
            body = ensureWrapped(body);
        }
        return body;
    }

    private RequestResult ensureWrapped(Object returnValue) {
        if (returnValue instanceof RequestResult) {
            return (RequestResult) returnValue;
        }
        return RequestResult.success(returnValue);
    }

}