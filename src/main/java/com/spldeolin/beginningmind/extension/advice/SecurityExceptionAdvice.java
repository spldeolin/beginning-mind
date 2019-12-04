package com.spldeolin.beginningmind.extension.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.security.exception.UnauthorizeException;
import com.spldeolin.beginningmind.security.exception.UnsignedException;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层Advice切面：异常处理
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Log4j2
public class SecurityExceptionAdvice {

    /**
     * 401 未登录
     */
    @ExceptionHandler(UnsignedException.class)
    public RequestResult handleUnsignException(UnsignedException e) {
        return RequestResult.failure(ResultCode.UNSIGNED, e.getMessage());
    }

    /**
     * 403 未授权
     */
    @ExceptionHandler(UnauthorizeException.class)
    public RequestResult handleUnauthorizeException(UnauthorizeException e) {
        return RequestResult.failure(ResultCode.FORBIDDEN, e.getMessage());
    }

}