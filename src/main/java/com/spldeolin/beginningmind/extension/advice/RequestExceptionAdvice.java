package com.spldeolin.beginningmind.extension.advice;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.extension.dto.InvalidDto;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 控制层Advice切面：Http请求相关异常处理
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestExceptionAdvice {

    @ExceptionHandler(ServletException.class)
    public RequestResult handle(ServletException e) {
        String message = e.getMessage();

        // 404 Not Found
        // 只有spring.mvc.throw-exception-if-no-handler-found=true和spring.resources.add-mappings=false时，才会抛出这个异常
        if (e instanceof NoHandlerFoundException) {
            log.warn(message);
            return RequestResult.failure(ResultCode.NOT_FOUND);
        }

        // 400 请求动词错误
        if (e instanceof HttpRequestMethodNotSupportedException) {
            String supportedMethods = Arrays
                    .toString(((HttpRequestMethodNotSupportedException) e).getSupportedMethods());
            message += " " + supportedMethods;
        }

        // 400 请求Content-Type错误。往往是因为后端的@RequestBody和前端的application/json没有同时指定或同时不指定导致的
        if (e instanceof HttpMediaTypeNotSupportedException) {
            message += " [application/json]";
        }

        log.warn(message);
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 请求Body格式错误
     * <pre>
     * 以下情况时，会被捕获：
     * alkdjfaldfjlalkajkdklf               可能是因为Body不是合法的JSON格式
     * (空)                                 JSON不存在
     * {"userAge"="notNumberValue"}         JSON中字段类型错误
     * </pre>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RequestResult httpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("message={}", e.getMessage());
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 请求Body内字段没有通过注解校验（通过参数级@Valid 启用的参数校验）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RequestResult handle(MethodArgumentNotValidException e) {
        log.warn("invalids={}", buildInvalids(e.getBindingResult()));
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    private Collection<InvalidDto> buildInvalids(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new InvalidDto().setPath(error.getField()).setValue(error.getRejectedValue())
                        .setReason(error.getDefaultMessage())).collect(Collectors.toList());
    }

}