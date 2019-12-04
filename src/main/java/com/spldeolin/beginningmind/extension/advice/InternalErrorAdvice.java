package com.spldeolin.beginningmind.extension.advice;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.spldeolin.beginningmind.extension.aspect.MethodCallValidatedAspect;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.extension.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.extension.exception.MethodCallInvalidException;
import com.spldeolin.beginningmind.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层Advice切面：Throwable异常处理
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Order
@Log4j2
public class InternalErrorAdvice {

    /**
     * @see MethodCallValidatedAspect#logError(org.aspectj.lang.JoinPoint, javax.validation.ConstraintViolationException)
     */
    @ExceptionHandler(MethodCallInvalidException.class)
    public RequestResult handleMethodCallInvalidException() {
        return RequestResult.failure(ResultCode.INTERNAL_ERROR);
    }

    /**
     * 500 内部BUG
     */
    @ExceptionHandler(Throwable.class)
    public RequestResult handle(Throwable e) {
        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }

        String insignia = track.getInsignia();
        log.warn("统一异常处理被击穿！标识：" + insignia, e);
        return RequestResult.failure(ResultCode.INTERNAL_ERROR);
    }

}