package com.spldeolin.beginningmind.extension.advice;

import java.lang.reflect.Type;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.util.JsonUtils;

/**
 * 用于获取@RequestBody参数对象，并将其加入到RequestTrack#more中
 *
 * @author Deolin 2020-11-22
 */
@Component
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
        RequestTrack.CURRENT.get().getMore().put("boundRequestBody", JsonUtils.compressJson(JsonUtils.toJson(body)));
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}