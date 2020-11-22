package com.spldeolin.beginningmind.extension;

import java.lang.reflect.Type;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;

/**
 * @author Deolin 2020-11-22
 */
@RestControllerAdvice
public class BoundRequestBodyObtainAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        RequestTrack.getCurrent().setBoundRequestBody(body);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}