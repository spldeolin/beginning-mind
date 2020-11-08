package com.spldeolin.beginningmind.extension.advice;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
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
import com.spldeolin.beginningmind.enums.ResultCodeEnum;
import com.spldeolin.beginningmind.extension.dto.InvalidDto;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 控制层Advice切面：Http请求相关异常处理
 *
 * @author Deolin
 * @see ResultCodeEnum
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestExceptionAdvice {

    /**
     * 404 Not Found
     * 只有spring.mvc.throw-exception-if-no-handler-found=true和spring.resources.add-mappings=false时，才会抛出这个异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RequestResult handler(NoHandlerFoundException e) {
        log.warn(e.getMessage());
        return RequestResult.failure(ResultCodeEnum.NOT_FOUND);
    }

    /**
     * 400 请求动词错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RequestResult handler(HttpRequestMethodNotSupportedException e) {
        String supportedMethods = Arrays.toString(e.getSupportedMethods());
        log.warn(e.getMessage() + " " + supportedMethods);
        return RequestResult.failure(ResultCodeEnum.BAD_REQEUST);
    }

    /**
     * 400 请求Content-Type错误。往往是因为后端的@RequestBody和前端的application/json没有同时指定或同时不指定导致的
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RequestResult handler(HttpMediaTypeNotSupportedException e) {
        log.warn(e.getMessage() + " " + " [application/json]");
        return RequestResult.failure(ResultCodeEnum.BAD_REQEUST);
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
        return RequestResult.failure(ResultCodeEnum.BAD_REQEUST);
    }

    /**
     * 400 请求Body内字段没有通过注解校验（通过参数级@Valid 启用的参数校验）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RequestResult handle(MethodArgumentNotValidException e) {
        log.warn("invalids={}", buildInvalids(e.getBindingResult()));
        return RequestResult.failure(ResultCodeEnum.BAD_REQEUST);
    }

    private Collection<InvalidDto> buildInvalids(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new InvalidDto().setPath(error.getField()).setValue(error.getRejectedValue())
                        .setReason(error.getDefaultMessage())).collect(Collectors.toList());
    }

}