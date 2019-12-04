package com.spldeolin.beginningmind.extension.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import com.spldeolin.beginningmind.common.BizException;
import com.spldeolin.beginningmind.constant.ResultCode;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层Advice切面：Http请求相关异常处理
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Log4j2
public class BizExceptionAdvice {

    /**
     * 1001 业务异常
     */
    @ExceptionHandler(BizException.class)
    public RequestResult handle(BizException e) {
        return RequestResult.failure(ResultCode.SERVICE_ERROR, e.getMessage());
    }

}