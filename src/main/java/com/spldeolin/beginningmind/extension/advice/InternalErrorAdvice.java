package com.spldeolin.beginningmind.extension.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.extension.aspect.MethodCallValidatedAspect;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.extension.exception.MethodCallInvalidException;
import com.spldeolin.beginningmind.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层Advice切面：内部BUG
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Log4j2
public class InternalErrorAdvice {

    /**
     * 500 未通过组件的参数校验
     *
     * @see MethodCallValidatedAspect 抛出方
     */
    @ExceptionHandler(MethodCallInvalidException.class)
    public RequestResult handleMethodCallInvalidException() {
        return RequestResult.failure(ResultCode.INTERNAL_ERROR);
    }

    /**
     * 500 无法预料的异常
     */
    @ExceptionHandler(Throwable.class)
    public RequestResult handle(Throwable e) {
        RequestTrack requestTrack = WebContext.getRequestTrack();
        if (requestTrack == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }

        String insignia = requestTrack.getInsignia();
        log.warn("统一异常处理被击穿！标识：" + insignia, e);
        return RequestResult.failure(ResultCode.INTERNAL_ERROR);
    }

}